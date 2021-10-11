# redis学习

数据结构

存储模型

事务

线程模型

持久化机制

集群 哨兵 主从  cluster 
数据淘汰策略  过期机制

客户端
jedis  redision 命令行



热点key问题

场景：高并发访问同一key，导致redis集群单个节点的负载过大
应对：1 根据应用场景看是否可以增加本地缓存(如guava cache)，将redis作为二级缓存
           2 将热点key散列成多个子key分散存储到redis集群各节点，各子key对应value值和原热点key相同，访问时           随机取，分散压力。

```java
   // redis集群
   private final static String redisClusterConnection = "192.168.1.100:5768,192.168.1.101:5769,192.168.1.102:5770";
    // 获取热点因子
    private int getHotFactor(){
        String[] redisNodes = redisClusterConnection.split(",");
        int redisNodeQuantity = null == redisNodes ? 1 : redisNodes.length;
        int factor = 1 + redisNodeQuantity / 3;
        return factor;
    }
    // 获取分布式锁(获取锁超时时间，锁本身超时时间)
    public String acquireLockWithTimeOut(String lockName, long acquireTimeOutMills, long lockTimeOutMills){
        String identifier = UUIDUtils.getId();
        String lock = "lock:" + lockName;
        long endTime = System.currentTimeMillis() + acquireTimeOutMills;
        do {
            if (getJedisCluster().setnx(lock, identifier) == 1) {
                getJedisCluster().expire(lock, lockTimeOutMills);
                return identifier;
            } else if (getJedisCluster().ttl(lock) == -1) {
                getJedisCluster().expire(lock, lockTimeOutMills);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {

            }
        } while (System.currentTimeMillis() < endTime);
        return null;
    }
    // 释放分布式锁
    public boolean releaseLock(String lockName, String identifier) {
        String lock  = "lock:" + lockName;
        if (Objects.equal(identifier, getJedisCluster().get(lock))) {
            getJedisCluster().del(lock);
            return true;
        }
        return false;
    }

    // 查询热点数据（随机取一个子key即可）
    public String getHot(String key) {
        int suffix = RandomUtils.nextInt(getHotFactor());
        String newKey = key + ":" + suffix;
        return jedisCluster.get(newKey);
    }
    // 设置热点key
    public void setHot(String key, int ttl, String value) {
        String identifier = acquireLockWithTimeOut(key, HOT_GET_LOCK_TTL, HOT_LOCK_TTL);
        if (StringUtils.isEmpty(identifier)) {
            return;
        }
        try {
            for (int suffix=0; suffix<getHotFactor(); suffix++) {
                String newKey = key + ":" + suffix;
                jedisCluster.setex(newKey, ttl, value);
            }
        } catch (Exception e) {
        } finally {
            releaseLock(key, identifier);
        }
    }
   // 删除热点key
    public void delHot(String key) {
        String identifier = acquireLockWithTimeOut(key, HOT_GET_LOCK_TTL, HOT_LOCK_TTL);
        if (StringUtils.isEmpty(identifier)) {
            return;
        }
        try {
            for (int suffix=0; suffix<getHotFactor(); suffix++) {
                String newKey = key + ":" + suffix;
                jedisCluster.del(newKey);
            }
        } catch (Exception e) {
        } finally {
            releaseLock(key, identifier);
        }
    }
```