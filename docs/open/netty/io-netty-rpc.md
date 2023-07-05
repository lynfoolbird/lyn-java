select、poll、epoll

# JavaIO

BIO、NIO、AIO

IO流 装饰者模式

IO多路复用

让单个线程监听多个网络连接，一旦某个连接就绪也就是触发读写事件的时候就会通知应用程序主动获取就绪的连接进行读写操作，也就是在应用程序里面可以用单个线程同时处理多个客户端连接，在对系统资源消耗比较小的情况下去提升服务端的连接处理数量，在io多路复用机制的实现原理，客户端请求到服务端后，此时客户端在传输数据过程中，为了避免server端在read客户端数据的时候阻塞，服务端会把请求注册到一个selector
的一个复路器上，服务端此时不需要等待，只需要启用一个线程通过selector.select方法去阻塞轮询复路器上就绪的channel，也就是说如果某个客户端数据传输完成以后，select方法就会返回这个就绪的channel，然后执行相关的处理逻辑。

Linux上有三种多路复用技术实现，select和poll是基于轮询方式获取就绪的连接，epoll基于事件驱动的方式。

# 线程模型

Reactor、Proactor

Redis线程模型

accept、read、decode、process、encode、write、send

## Reactor模型

无论是C++还是Java编写的网络框架，大多数都是基于Reactor模型进行设计和开发，Reactor模型基于事件驱动，特别适合处理海量的I/O事件。

Reactor模型定义的三种角色：

- Reactor：负责监听和分配事件，将I/O事件分派给对应的Handler。新的事件包含连接建立就绪、读就绪、写就绪等。
- Acceptor：处理客户端新连接，并分派请求到处理器链中。
- Handler：将自身与事件绑定，执行非阻塞读/写任务，完成channel的读入，完成处理业务逻辑后，负责将结果写出channel。可用资源池来管理。

Reactor模型处理请求的流程：

读取操作：

1. 应用程序注册读就绪事件和相关联的事件处理器
2. 事件分离器等待事件的发生
3. 当发生读就绪事件的时候，事件分离器调用第一步注册的事件处理器

写入操作类似于读取操作，只不过第一步注册的是写就绪事件。

Reactor模型具有如下的优点：

1. 响应快，不必为单个同步时间所阻塞，虽然Reactor本身依然是同步的；
2. 编程相对简单，可以最大程度的避免复杂的多线程及同步问题，并且避免了多线程/进程的切换开销；
3. 可扩展性，可以方便地通过增加Reactor实例个数来充分利用CPU资源；
4. 可复用性，Reactor模型本身与具体事件处理逻辑无关，具有很高的复用性。

### 单Reactor单线程

Reactor线程负责多路分离套接字，accept新连接，并分派请求到handler。**Redis使用单Reactor单进程的模型**。

![img](images/io-single-reactor-single-thread.png)

消息处理流程：

1. Reactor对象通过select监控连接事件，收到事件后通过dispatch进行转发。
2. 如果是连接建立的事件，则由acceptor接受连接，并创建handler处理后续事件。
3. 如果不是建立连接事件，则Reactor会分发调用Handler来响应。
4. handler会完成read->业务处理->send的完整业务流程。

单Reactor单线程模型只是在代码上进行了组件的区分，但是整体操作还是单线程，不能充分利用硬件资源。handler业务处理部分没有异步。

对于一些小容量应用场景，可以使用单Reactor单线程模型。但是对于高负载、大并发的应用场景却不合适，主要原因如下：

1. 即便Reactor线程的CPU负荷达到100%，也无法满足海量消息的编码、解码、读取和发送。
2. 当Reactor线程负载过重之后，处理速度将变慢，这会导致大量客户端连接超时，超时之后往往会进行重发，这更加重Reactor线程的负载，最终会导致大量消息积压和处理超时，成为系统的性能瓶颈。
3. 一旦Reactor线程意外中断或者进入死循环，会导致整个系统通信模块不可用，不能接收和处理外部消息，造成节点故障。

为了解决这些问题，演进出单Reactor多线程模型。

### 单Reactor多线程模型

该模型在事件处理器（Handler）部分采用了多线程（线程池）。

![img](images/io-single-reactor-multi-thread.png)

消息处理流程：

1. Reactor对象通过Select监控客户端请求事件，收到事件后通过dispatch进行分发。
2. 如果是建立连接请求事件，则由acceptor通过accept处理连接请求，然后创建一个Handler对象处理连接完成后续的各种事件。
3. 如果不是建立连接事件，则Reactor会分发调用连接对应的Handler来响应。
4. Handler只负责响应事件，不做具体业务处理，通过Read读取数据后，会分发给后面的Worker线程池进行业务处理。
5. Worker线程池会分配独立的线程完成真正的业务处理，如何将响应结果发给Handler进行处理。
6. Handler收到响应结果后通过send将响应结果返回给Client。

相对于第一种模型来说，在处理业务逻辑，也就是获取到IO的读写事件之后，交由线程池来处理，handler收到响应后通过send将响应结果返回给客户端。这样可以降低Reactor的性能开销，从而更专注的做事件分发工作了，提升整个应用的吞吐。

但是这个模型存在的问题：

1. 多线程数据共享和访问比较复杂。如果子线程完成业务处理后，把结果传递给主线程Reactor进行发送，就会涉及共享数据的互斥和保护机制。
2. Reactor承担所有事件的监听和响应，只在主线程中运行，可能会存在性能问题。例如并发百万客户端连接，或者服务端需要对客户端握手进行安全认证，但是认证本身非常损耗性能。

为了解决性能问题，产生了第三种主从Reactor多线程模型。

### 主从Reactor多线程模型

比起第二种模型，它是将Reactor分成两部分：

1. mainReactor负责监听server socket，用来处理网络IO连接建立操作，将建立的socketChannel指定注册给subReactor。
2. subReactor主要做和建立起来的socket做数据交互和事件业务处理操作。通常，subReactor个数上可与CPU个数等同。

**Nginx、Swoole、Memcached和Netty都是采用这种实现。**

![img](images/io-master-slave-reactor-multi-thread.png)

消息处理流程：

1. 从主线程池中随机选择一个Reactor线程作为acceptor线程，用于绑定监听端口，接收客户端连接

2. acceptor线程接收客户端连接请求之后创建新的SocketChannel，将其注册到主线程池的其它Reactor线程上，由其负责接入认证、IP黑白名单过滤、握手等操作

3. 步骤2完成之后，业务层的链路正式建立，将SocketChannel从主线程池的Reactor线程的多路复用器上摘除，重新注册到Sub线程池的线程上，并创建一个Handler用于处理各种连接事件

4. 当有新的事件发生时，SubReactor会调用连接对应的Handler进行响应

5. Handler通过Read读取数据后，会分发给后面的Worker线程池进行业务处理

6. Worker线程池会分配独立的线程完成真正的业务处理，如何将响应结果发给Handler进行处理

7. Handler收到响应结果后通过Send将响应结果返回给Client。



# Netty

## 对Netty的理解？

Netty是一款高性能的NIO网络通信框架，提供了简单易用的API，可以利用这些API开发网络应用程序；Netty在nio的基础上做了很多优化，比如零拷贝、高性能无锁队列、内存池等，性能比NIO高；Netty支持多种通信协议，内置多种拆包策略，使用方便。

## 为什么使用Netty？

- 统一的 API，支持多种传输类型，阻塞和非阻塞的。
- 简单而强大的线程模型。
- 自带编解码器解决 TCP 粘包/拆包问题。
- 自带各种协议栈。
- 真正的无连接数据包套接字支持。
- 比直接使用 Java 核心 API 有更高的吞吐量、更低的延迟、更低的资源消耗和更少的内存复制。
- 安全性不错，有完整的 SSL/TLS 以及 StartTLS 支持。
- 社区活跃
- 成熟稳定，经历了大型项目的使用和考验，而且很多开源项目都使用到了
  Netty， 比如我们经常接触的 Dubbo、RocketMQ 等等。 
  


## Netty应用场景

RPC框架网络通信、http服务器、消息推送系统



## Netty核心组件及作用



## Netty零拷贝机制



## Netty实现RPC框架



## 百万连接架构

  

  

  

  