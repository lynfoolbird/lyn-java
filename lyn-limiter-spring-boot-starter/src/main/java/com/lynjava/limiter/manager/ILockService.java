package com.lynjava.limiter.manager;

public interface ILockService {
    void lock(String lockKey, String lockValue);

    void unLock(String lockKey, String lockValue);
}
