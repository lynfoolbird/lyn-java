package com.lynjava.distributedlock.service;

import com.lynjava.distributedlock.api.ILockService;

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
