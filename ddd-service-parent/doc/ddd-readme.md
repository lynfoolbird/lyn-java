# DDD工程文档

## DDD工程模块依赖关系

![img](files/ddd-modules-depenency.png)



## 开发规范

命名、统一语言、日志、异常码
望文生义
01_ddl_create_table_tablename_工号.sql

包命名：
com.lynjava.子产品名.子产品模块(服务名).api、app、domain、external、persistence、common、provider；
api/app/domain以子域为维度、persistence以聚合为维度，用包名区分；
子域内每个聚合一个repo，对应一个或多个Dao，每个实体对应一个Dao，值对象不具有Dao；
api实现类中编排应用服务，应用服务编排领域服务，聚合(子域内)在domain层；防腐层external接口在domain层声明；
跨3个及以上聚合复杂查询可走bff通道，单独出来Dao和mapper


### DDD规范
聚合根和实体设计需充分实现充血模型，加入必要的行为，比如参数校验，转换redis value等
聚合间通过聚合根id协同，聚合内可以直接引用对象；
聚合间数据要求最终一致性，聚合内要保持强一致性；


### 依赖规范

最小化引用，按需使用；
依赖尽量下移；

api层依赖硬尽可能少，不建议依赖项目内common模块，可依赖项目间common；
app层和common成管理主要依赖项
parent里面原则上只作依赖包版本管理及极少的通用依赖

### 分支管理

上线分支：release_beta_上线时间；
开发分支：develop；
迭代分支：dev_8.8_team  基于develop分支拉；
个人分支： dev_8.8_team_工号  基于迭代分支拉

### 调用规范

1 服务内跨子域跨聚合调用，在app层编排领域服务
2 系统内各服务间调用，在防腐层用rpc
3 外部系统调用，在防腐层用http，feign

TODO
工程一 springboot ddd
1 部署：打出的war包部署到tomcat启动问题？如何打出可执行jar？pom依赖管理
2 统一异常处理，统一返回封装，日志ELK、参数校验（validator、jsonschema、代码）、启动后执行、
  pojo转换工具类MapStruct、dozer 深拷贝 浅拷贝; spring event, spring batch
  https://mp.weixin.qq.com/s/lZr-gTAKoA_FJSshu7KXvA
  https://cloud.tencent.com/developer/article/1650522
3 过滤器、拦截器、自定义注解、AOP
4 mybatis集成 mysql集成 连接池 事务  动态多数据源 cxf集成swagger
5 springevent、动态加载bean，启动后执行runner；异步@Async、定时调度@Schedule、事务@Transaction、线程池
  RestTemplate http连接池
6 中间件集成：redis、kafka，es，mycat、任务调度xxljob
7 分布式：zk

工程二 dubbo demo

工程三 spring cloud demo