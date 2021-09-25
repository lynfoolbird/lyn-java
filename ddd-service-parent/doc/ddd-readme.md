# DDD工程文档

## DDD工程模块依赖关系

![img](files/ddd-modules-depenency.png)

## 开发规范

### 依赖规范

最小化引用，按需使用；

### 分支管理

上线分支：release_beta_上线时间

开发分支：develop

迭代分支：dev_8.8_team  基于develop分支拉

个人分支： dev_8.8_team_工号  基于迭代分支拉

TODO
1 部署：打出的war包部署到tomcat启动问题？如何打出可执行jar？pom依赖管理
2 统一异常处理，统一返回封装，日志ELK、参数校验（validator、jsonschema、代码）、启动后执行、
  pojo转换工具类MapStruct、dozer 深拷贝 浅拷贝; spring event, spring batch
  https://mp.weixin.qq.com/s/lZr-gTAKoA_FJSshu7KXvA
  https://cloud.tencent.com/developer/article/1650522
3 过滤器、拦截器、自定义注解、AOP
4 mybatis集成 mysql集成 连接池 事务  动态多数据源
5 springevent、动态加载bean，启动后执行runner；异步@Async、定时调度@Schedule、事务@Transaction、线程池
6 中间件集成：redis、kafka，es，mycat、任务调度xxljob
7 分布式：zk