RPC原理

# 为什么要用RPC?

服务化/微服务

分布式系统架构

服务可重用

系统间交互调用

# RPC概念

RPC（Remote Procedure Call Protocol）— 远程过程调用协议，一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。RPC协议假定某些传输协议的存在，如TCP或UDP，为通信程序之间携带信息数据。在OSI网络通信模型中，RPC跨越了**传输层**和**应用层**。RPC使得开发包括网络分布式多程序在内的应用程序更加容易。

过程就是业务处理、计算任务，更直白理解，就是程序。像调用本地方法一样调用远程的过程。

RPC采用Client-Server结构，通过request-response消息模式实现。

![img](images/rpc-shiyi.png)

RPC的三个过程：

通信协议：

寻址方式：比如ip地址及端口

序列化：

RPC核心概念术语：

Client、Server、calls、replies、services，programs，procedures，version，marshalling(编组)、unmarshalling(解组)
 一个网络服务由一个或多个远程程序集构成

  一个远程程序实现一个或多个远程过程

 过程、过程的参数、结果在程序协议说明书中定义说明

 为兼容程序协议变更、一个服务端可能支持多个版本的远程程序

# RPC协议

RPC调用过程中需要将参数编组为消息进行发送，接收方需要解组消息为参数，过程处理结果同样需要经编组、解组。消息由哪些部分构成及消息的表示形式就构成了消息协议。RPC调用过程中采用的消息协议称为RPC协议。

RPC是要做的事，RPC协议规定请求、响应消息的格式。在TCP（网络传输控制协议）之上我们可以选用或自定义消息协议来完成我们的RPC消息交互。我们可以选用通用的标准协议，如：http、https。也可根据自身的需要定义自己的消息协议！

RPC与RMI有什么区别？我们熟悉的webservice、restfull接口调用是RPC吗？

RMI(remote method invocation) 远程方法调用是oop领域中RPC的一种具体实现。

都是RPC，只是消息的组织方式、消息协议不同罢了！

**RPC vs MQ**

在架构上，RPC和MQ的差异点是，Message Queue有一个中间结点Queue，可以把消息存储。

RPC的特性：

同步调用，对于要等待返回结果的场景，RPC可以非常自然直觉的使用 (RPC也可以是异步调用)。由于等待结果， Consumer会有线程消耗。如果以异步RPC的方式使用，Consumer线程消耗可以去掉。但不能做到像MQ一样暂存请求，压力会直接传导到服务Provider。

消息的特性：

1.Message Queue把请求的压力保存一下，逐渐释放出来，让处理者按照自己的节奏来处理。

2.Message Queue引入一下新的结点，系统的可靠性会受Message Queue结点的影响。

3.Message Queue是异步单向的消息。发送消息设计成是不需要等待消息处理的完成。

所以对于有同步返回需求，用Message Queue则变得麻烦了。

RPC和MQ适用场景：

1.希望同步得到结果的场合，RPC合适。

2.希望使用简单，则RPC；RPC操作基于接口，使用简单，使用方式模拟本地调用。异步的方式编程比较复杂。

3.不希望发送端受限于处理端的速度时，使用Message Queue。

4.随着业务增长，有的处理端处理量会成为瓶颈，会进行同步调用到异步消息的改造。

![1634043712810](images/rpc-mq.png)

# RPC流程

RPC流程

![1634044005314](images/rpc-flow.png)

1.客户端处理过程中调用Client stub（就像调用本地方法一样），传入参数；

2.Client stub 将参数**编组**为消息，然后通过系统调用向服务端发送消息；


3.客户端本地操作系统将消息从客户端机器发送到服务端机器；


4.服务端操作系统将接收到的数据包传递给Server stub；

5.Server stub **解组**消息为参数；


6.Sever stub 再调用服务端的过程，过程执行结果以反方向的相同步骤响应给客户端。

stub（存根）：分布式计算中存根是一段代码，它转换在远程过程调用期间Client和server之间传递的参数。



RPC流程中需要处理的问题

1.Client stub 、Server stub的开发；

2.参数如何编组为消息，以及解组消息；


3.消息如何发送；


4.过程结果如何表示、异常情况如何处理；


5.如何实现安全的访问控制。


# RPC框架

封装好了参数编组、消息解组、底层网络通信的RPC程序开发框架，让我们可以直接在其基础上只需专注于我们的过程代码编写。

常用RPC框架：

java领域：

传统webservice框架：Apache CXF、Apache Axis2、java 自带的 JAX-WS等等。webService框架大多基于标准的SOAP协议。

新兴的微服务框架：Dubbo、spring cloud、Apache Thrift 、ICE、gRPC等等

# RPC服务暴露

远程提供者需要以某种形式提供服务调用相关的信息，包括但不限于服务接口定义、数据结构、或者中间态的服务定义文件。例如Facebook的Thrift的IDL文件，Web service的WSDL文件；服务的调用者需要通过一定的途径获取远程服务调用相关的信息。

![1634045272473](images/rpc-service-baolu.png)

# RPC涉及关键技术

## 动态代理

服务调用者用的服务实际是远程服务的本地代理。说白了就是通过动态代理来实现。

java 里至少提供了两种技术来提供动态代码生成，一种是 jdk 动态代理，另外一种是字节码生成。


动态代理相比字节码生成使用起来更方便，但动态代理方式在性能上是要逊色于直接的字节码生成的，而字节码生成在代码可读性上要差很多。


两者权衡起来，牺牲一些性能来获得代码可读性和可维护性显得更重要。

## 网络通信

RPC框架通信与具体的协议无关。RPC 可基于 HTTP 或 TCP 协议，Web Service 就是基于 HTTP 协议的 RPC，它具有良好的跨平台性，但其性能却不如基于TCP 协议的 RPC。

![1634046054544](images/rpc-tongxin.png)

## 序列化

两方面会直接影响 RPC 的性能，一是传输方式，二是序列化

![1634046257642](images/rpc-xuliehua.png)

Protobuf是跨语言的。

# 手写RPC

基于Netty和Protobuf实现lynRPC，用zookeeper作注册中心，支持SPI扩展

客户端 依赖接口， clientStub动态代理对象（接口代理），完成请求消息编组（包括接口类、方法、及参数等），发送网络请求，对响应消息解组

服务端 实现接口，serverStub动态代理对象（实现类代理），请求消息解组，执行方法，响应消息（包括响应结果等）编组

服务注册与发现