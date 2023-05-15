package com.lynjava.limiter.manager.impl;

import com.lynjava.limiter.manager.ILockService;

public class DbLockServiceImpl implements ILockService {
    @Override
    public void lock(String lockKey, String lockValue) {
        System.out.println("db lock key:" + lockKey);
    }

    @Override
    public void unLock(String lockKey, String lockValue) {
        System.out.println("db unlock key:" + lockKey);
    }
}
