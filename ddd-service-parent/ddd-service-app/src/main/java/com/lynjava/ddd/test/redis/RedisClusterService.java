package com.lynjava.ddd.test.redis;//package com.lyn.mvnprj.service.redis;
///*
//import com.google.common.base.Objects;
//import com.google.common.collect.Sets;
//import com.lyn.utils.UUIDUtils;
//import org.apache.commons.lang3.RandomUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.NumberUtils;
//import org.springframework.util.StringUtils;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import javax.annotation.PostConstruct;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by liyanan on 2018/5/20.
// */
//public class RedisClusterService {
//
//    private String password;
//
//    private int connTimeOut;
//    private int maxTotal;
//    private int maxIdle;
//    private long maxWaitMills;
//
//    private static int HOT_SCATTER_NUM;
//
//    public static final int HOT_GET_LOCK_TTL = 20;
//    public static final int  HOT_LOCK_TTL = 2000;
//
//    @Value("${redis.cluster.connection}")
//    private String redisClusterConnection = "192.168.200.10:5678,192.168.200.11:5679";
//
//    private JedisCluster jedisCluster;
//
//    @PostConstruct
//    public void init(){
//        Set<HostAndPort> jedisClusterNodes = Sets.newHashSet();
//        for (String hostAndPort:redisClusterConnection.split(",")) {
//            String[] array = hostAndPort.split(":");
//            jedisClusterNodes.add(new HostAndPort(array[0], NumberUtils.parseNumber(array[1], Integer.class)));
//        }
//        int maxAttempts = 5;
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
//        jedisPoolConfig.setTestOnBorrow(true);
//
//        jedisCluster = new JedisCluster(jedisClusterNodes, connTimeOut, connTimeOut, maxAttempts,password, jedisPoolConfig);
//        int redisNodeQuantity = jedisCluster.getClusterNodes().size();
//        HOT_SCATTER_NUM = 1 + redisNodeQuantity / 3;
//    }
//
//    public JedisCluster getJedisCluster(){
//        return this.jedisCluster;
//    }
//
//    // 获取热点因子
//    private int getHotFactor(){
//        String[] redisNodes = redisClusterConnection.split(",");
//        int redisNodeQuantity = null == redisNodes ? 1 : redisNodes.length;
//        int factor = 1 + redisNodeQuantity / 3;
//        return factor;
//    }
//    // 获取分布式锁(获取锁超时时间，锁本身超时时间)
//    public String acquireLockWithTimeOut(String lockName, long acquireTimeOutMills, long lockTimeOutMills){
//        String identifier = UUIDUtils.getId();
//        String lock = "lock:" + lockName;
//        long endTime = System.currentTimeMillis() + acquireTimeOutMills;
//        do {
//            if (getJedisCluster().setnx(lock, identifier) == 1) {
//                getJedisCluster().pexpire(lock, lockTimeOutMills);
//                return identifier;
//            } else if (getJedisCluster().ttl(lock) == -1) {
//                getJedisCluster().pexpire(lock, lockTimeOutMills);
//            }
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (Exception e) {
//
//            }
//        } while (System.currentTimeMillis() < endTime);
//        return null;
//    }
//    // 释放分布式锁
//    public boolean releaseLock(String lockName, String identifier) {
//        String lock  = "lock:" + lockName;
//        if (Objects.equal(identifier, getJedisCluster().get(lock))) {
//            getJedisCluster().del(lock);
//            return true;
//        }
//        return false;
//    }
//    // 查询热点数据（随机取一个子key即可）
//    public String getHot(String key) {
//        int suffix = RandomUtils.nextInt(0, getHotFactor());
//        String newKey = key + ":" + suffix;
//        return jedisCluster.get(newKey);
//    }
//    // 设置热点数据
//    public void setHot(String key, int ttl, String value) {
//        String identifier = acquireLockWithTimeOut(key, HOT_GET_LOCK_TTL, HOT_LOCK_TTL);
//        if (StringUtils.isEmpty(identifier)) {
//            return;
//        }
//        try {
//            for (int suffix=0; suffix<getHotFactor(); suffix++) {
//                String newKey = key + ":" + suffix;
//                jedisCluster.setex(newKey, ttl, value);
//            }
//        } catch (Exception e) {
//        } finally {
//            releaseLock(key, identifier);
//        }
//    }
//    // 删除热点数据
//    public void delHot(String key) {
//        String identifier = acquireLockWithTimeOut(key, HOT_GET_LOCK_TTL, HOT_LOCK_TTL);
//        if (StringUtils.isEmpty(identifier)) {
//            return;
//        }
//        try {
//            for (int suffix=0; suffix<getHotFactor(); suffix++) {
//                String newKey = key + ":" + suffix;
//                jedisCluster.del(newKey);
//            }
//        } catch (Exception e) {
//        } finally {
//            releaseLock(key, identifier);
//        }
//    }
//}
//*/