架构师面试：架构设计、系统设计、JVM、性能优化

绩效：业务目标；价值观与行为目标；组织与人员管理目标；个人能力提升目标；

危机意识  不可替代性

增加技能包 掌握核心科技 python与AI、go、安全、容器化k8s、docker、servicemesh、istio

亿级流量网站架构核心技术、大型网站性能优化、云原生、google sre、超大流量分布式系统架构、可伸缩架构、企业IT架构、人工智能

官网 思维导图 原理源码 应用场景 5W2H



规则引擎 流程引擎 工作流

八股文：楼仔 javaguide 3y  对线面试官  抖音程序员holis
saltstack

session、cookie、token、jwt、单点登录、用户管理（用户/角色/权限）、spring security、shiro、oauth2.0



github：735835816@qq.com   lynfoolbird/三南@生

mysql：root/Changeme_123

https://github.com/xingshaocheng/architect-awesome   architect-awesome  

https://github.com/AobingJava     敖丙Java

http://javainterview.gitee.io/luffy/   对线面试官

https://github.com/ZhongFuCheng3y/athena  3y

https://gitee.com/shishan100/Java-Interview-Advanced  石杉架构

https://gitee.com/SnailClimb/JavaGuide/          JavaGuide

https://github.com/TFdream TFdream 



阿里面试 https://mp.weixin.qq.com/s/gYpZCSYK3YYEvGFFeHdFJQ

https://blog.csdn.net/weixin_47066028/article/details/112887939?utm_medium=distribute.pc_relevant.none-task-blog-2~default~OPENSEARCH~default-5.pc_relevant_baidujshouduan&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~OPENSEARCH~default-5.pc_relevant_baidujshouduan

https://blog.csdn.net/java_fenxiang/article/details/86517010

https://segmentfault.com/a/1190000016172470

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

springboot 启动后执行方法：实现CommandLineRunner接口；实现ApplicationRunner接口；实现ApplicationListener接口；实现InitializingBean；@PostConstruct

SpEL获取List对象中某个字段集合 #serverMemberList.![id]

beans：加载过程、循环依赖 懒加载 注解原理及常用注解  beanfactory factorybean，aware接口

静态代理、动态代理 JDK cglib 区别 应用场景 aop  spring中使用

https://www.jianshu.com/p/9ea61d204559

Profile用途：配置区分环境、按环境加载不同bean；注解@Profile、xml配置

热加载：devtools、jrebel、idea-Run-DebuggingActions-ReloadChangedClass



## 2.2 mybatis
mybatis/mybatis plus jdbc orm 

分页插件实现原理、一级二级缓存、执行过程、只写接口原理

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

leetcode、牛客网、oj、剑指offer  刷题练习

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

solr、elastic search（分布式）

## 4.4 分库分表

mycat、sharding-jdbc

## 4.5 文件系统
FastDFS

## 4.6 任务调度
xxl-job、elasticJob、quartz、spring task、powerjob

## 4.7 WEB服务
tomcat
nginx lua openresty

Nginx；Java dsmiley.httpproxy包

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

负载均衡：分流；软负载均衡（DNS、LVS四层、nginx七层）、硬负载均衡 （F5）；客户端、服务端负载均衡；算法：随机、轮询、加权轮询、IP哈希、url哈希、最少连接、一致性hash等；Ribbon、LoadBalancer、Feign；DNS负载均衡-> F5 -> nginx负载均衡

远程调用：resttemplate、apache httpclient、okhttp、openfeign；rpc；

注册中心:

网关：

配置中心：

熔断器：

链路追踪：

高可用：HAProxy、keepalived

DDD CQRS 六边形架构  《实现领域驱动设计》

istio、service mesh

容器化 k8s docker  go语言


# 6 架构设计与性能调优
系统设计、架构设计 设计思想 重构、设计模式、架构模式、设计原则SOLIDCD、面向对象、AOP
秒杀系统设计、抢红包业务设计
三高：高并发、高可用、高性能 系统设计
代理模式、观察者模式、单例、模板方法   spring mybatis与设计模式  ifelse重构思路

性能调优  容量设计

问题处理：fullgc、cpu高、内存高、oom、stackoverflow、内存泄漏、频繁gc



⾼可⽤架构设计： 分布式、超时和重试机制、集群容错机制、应对接⼝级故障： 降级、 熔断、 限流、排队；哨兵机制、keepalived

大数据量：分治、并发、筛选、定期清理

多线程设计模式

连接池：c3p0、dbcp；druid、HikariCP

远程调用：RMI、springhttpinvoke、webservice、rpc、http、restful


# 7 善用工具
maven/gradle

git

idea

代码生成：mybatis-generator、easy-code、swagger-codegen

mybatis-plus

工具类库：jdk、spring、guava、hutool、apache-commons...

# 8 项目经验

项目架构、数据流、技术栈、遇到的难题及解决方案、项目潜在瓶颈、

静态路由同步：多线程、批量insert、压缩

容器上报重复:redis 队列，分发，hash取模线程消费



亮点、难题及解决方案、架构、技术栈

大厂面经、互联网面经

方法论：PDCA 四象限 SMART 5W2H

leader思维  架构师思维  通用 扩展



职业规划未来规划：架构师/驾照...教师资格证？

