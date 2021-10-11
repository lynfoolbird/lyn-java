本地缓存
ConcurrentHashMap缺乏淘汰策略
LRUMap全局锁竞争 不支持过期时间 不支持自动刷新
Ehcache支持集群支持持久化
guava cache
caffeine

分布式缓存

redis  Memcache

spring缓存注解Cacheable

缓存设计
项目中如何使用缓存

最佳实践 本地缓存作一级缓存 分布式缓存作二级缓存然后db层

选型

淘汰算法: LRU  LFU  FlFO tinyLFU
命中率




