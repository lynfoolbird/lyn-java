重点面试题：如注解原理，hashmap、Concurrenthashmap...

架构师面试：架构设计、系统设计、JVM、性能优化

github：735835816@qq.com   lynfoolbird/三南@生

gitee：



绩效：业务目标；价值观与行为目标；组织与人员管理目标；个人能力提升目标；



beans：加载过程、循环依赖 懒加载 注解原理及常用注解  beanfactory factorybean，aware接口

Nginx；Java dsmiley.httpproxy包

静态代理、动态代理 JDK cglib 区别 应用场景 aop  spring中使用



危机意识  不可替代性

增加技能包 掌握核心科技 python与AI、go、安全

https://blog.csdn.net/weixin_47066028/article/details/112887939?utm_medium=distribute.pc_relevant.none-task-blog-2~default~OPENSEARCH~default-5.pc_relevant_baidujshouduan&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~OPENSEARCH~default-5.pc_relevant_baidujshouduan

https://blog.csdn.net/java_fenxiang/article/details/86517010

https://blog.csdn.net/weixin_34381687/article/details/88740638?utm_medium=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-1.pc_relevant_baidujshouduan&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-1.pc_relevant_baidujshouduan

# 1  Java基础

基础、集合、多线程、IO流 网络编程
高级： JDK特性、JVM

btrace javaassistant 字节码技术
servlet技术
hashmap 初始容量16 加载因子、死循环 1.7与1.8区别 红黑树
concurrenthashmap
aqs cas 内存泄漏
threadlocal

订单 购买 退订(负向订单 而不是在原订单修改)

json工具：hijson  fastjson jackson json-path

可配置 可扩展 可维护 可读性

## 1.1 语言基础

释放锁的场景：

1 执行完同步代码快

2 异常线程终止

3 执行wait()方法

## 1.2 JVM
JDK特性、JVM

# 2 开源框架

学习源码

## 2.1 spring
spring/springmvc/springboot

spring循环依赖、spring设计模式、IOC、AOP、MVC流程

自动配置原理、手写starter、启动过程、与springmvc区别

## 2.2 mybatis
mybatis/mybatis plus jdbc orm 分页插件实现原理、一级二级缓存、执行过程、只写接口原理

常用标签、#$区别

## 2.3 dubbo

## 2.4 spring cloud
spring cloud/spring cloud alibaba
nacos、sentinel

## 2.5 Netty

# 3 计算机基础
## 3.1 数据库
索引、sql优化
mysql
oracle

## 3.2 数据结构与算法

红黑树

leetcode、牛客网、oj、剑指offer

labuladong的算法小抄  github、gitee

编程题

1 树操作 删除一个节点后求剩余节点

## 3.3 操作系统

Linux

## 3.4 网络编程

计算机网络

长连接短链接
web请求流程
http1.0 1.1
http https

tcp 三次握手四次挥手

网络编程socket、nio，netty

# 4 中间件
## 4.1 缓存

### redis

缓存淘汰算法LRU/LFU

caffeine、分布式缓存

## 4.2  消息队列
消息不丢失、重复消费、顺序性消费、消息堆积

死信队列

MQ可靠传输实现、高可用如何保证

### kafka

## 4.3 搜索引擎
Lucene：https://mp.weixin.qq.com/s/Mz5XI9leHHhfbLTfZ4wCBw

Solr、elastic search

## 4.4 分库分表

mycat

## 4.5 文件系统
FastDFS

## 4.6 任务调度
xxl-job、elasticJob、quartz、spring task

## 4.7 WEB服务
tomcat
nginx lua openresty

# 5 分布式微服务

算法：paxos raft

理论：cap base

框架：dubbo、springcloud、springcloudalibaba

问题及方案：分布式锁、分布式ID、分布式事务、分布式配置、分布式会话、幂等性、防重复提交、跨域

组件：zk、注册中心、配置中心、网关、声明式调用feign、断路器、阿里sentinel

技术：rpc（RMI、webservice、soap、cxf、jax-rs，jax-ws、rest、restful、http）、


单点登录SSO session cookie jwt

服务拆分

服务治理：服务注册与发现、负载均衡、 熔断、限流、降级、容错failover、超时重试、链路追踪、分布式日志ELK、监控

限流：限流算法(固定窗口计数器、滑动窗口、令牌桶、漏桶算法)；接入层限流（nginx）、应用层限流（单机限流、分布式限流）；工具：guava ratelimit、hystrix、阿里sentinel 原理；自定义注解+AOP、自定义注解+拦截器+Redis+Lua；

负载均衡：分流；软负载均衡（DNS、LVS四层、nginx七层）、硬负载均衡 （F5）；客户端、服务端负载均衡；算法：随机、轮询、加权轮询、IP哈希、url哈希、最少连接等；Ribbon、LoadBalancer、Feign；DNS负载均衡-> F5 -> nginx负载均衡

高可用：HAProxy、keepalived



DDD CQRS 六边形架构

《实现领域驱动设计》

跨子域要在app层编排；聚合(子域内)在domain层；防腐层external接口在app层、domain层声明；

api/app/domain/persit  每个子域一个模块 用包区分；跨聚合用id引用而非对象引用；

子域内每个聚合一个repo，对应一个或多个Dao，每个实体对应一个Dao，值对象不具有Dao


# 6 架构设计与性能调优
系统设计、架构设计 设计思想
重构、设计模式、架构模式、设计原则SOLIDCD、面向对象、AOP
秒杀系统设计、抢红包业务设计
三高：高并发、高可用、高性能 系统设计
代理模式、观察者模式、单例、模板方法
spring mybatis与设计模式
ifelse重构思路

读写分离、动静分离、前后端分离

性能调优
问题处理：fullgc、cpu高、内存高、oom、stackoverflow、内存泄漏、频繁gc
性能优化思想：本地化(如引入二级本地缓存，微服务注册中心数据本地化)、池化技术(连接池对象池线程池等)、缓冲（BufferMQ等）、缓存、异步、多线程并发并行、分布式、分流(负载均衡)、批处理、数据结构与算法（时间换空间，空间换时间等）


# 7 善用工具
maven
git
gradle
idea

代码生成

mybatis-generator

easy-code

swagger-codegen

mybatis-plus

# 8 项目经验

项目架构、数据流、技术栈、遇到的难题及解决方案、项目潜在瓶颈、

静态路由同步：多线程、批量insert

静态路由同步：多线程、批量insert

亮点、难题及解决方案、架构、技术栈

大厂面经、互联网面经



