性能调优

三高思想：本地化(如引入二级本地缓存，微服务注册中心数据本地化，远程调用落地)、池化技术(内存池连接池对象池线程池等)、缓存缓冲（BufferMQ本地缓存分布式缓存等）、异步化(MQ，springevent、线程等，非核心逻辑异步处理)、多线程并发并行、分布式(系统服务拆分、分库分表分区sql索引优化，锁事务索引，字段冗余，熔断限流降级)、分流(负载均衡)、批量批处理（比如请求合并）、数据结构与算法（时间换空间，空间换时间等）、读写分离(mysql、redis)、动静分离，合适的工具(关系型数据库，mongodb、es、mysql、hbase等)、代码层面(锁事务上下文传递集合初始容量等)，压缩传输（gzip等）、回调机制

预处理与延迟处理（如缓存预热）、CPUIO（零拷贝、IO复用、无锁编程、多线程、缓存友好、高效序列化）

https://www.toutiao.com/article/7152327136030835232/?log_from=53e12154ad351_1670165982743&from_pwa=1
https://www.toutiao.com/article/7167167458451866151/?log_from=93a1e75_1668956959141
https://mp.weixin.qq.com/s/ujDIauY9YbUWjto1IfruAQ

![img](images/interface-turning-1.png)

问题处理

