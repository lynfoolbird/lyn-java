package com.lynjava.limiter.manager.impl;

import com.lynjava.limiter.manager.ILockService;

public class RedisLockServiceImpl implements ILockService {
    @Override
    public void lock(String lockKey, String lockValue) {
        System.out.println("Redis lock key:" + lockKey);
    }

    @Override
    public void unLock(String lockKey, String lockValue) {
        System.out.println("Redis unlock key:" + lockKey);
    }
}
