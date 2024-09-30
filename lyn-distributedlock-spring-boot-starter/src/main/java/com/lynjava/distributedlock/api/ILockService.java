package com.lynjava.distributedlock.api;

public interface ILockService {
    void lock(String lockKey, String lockValue);

    void unLock(String lockKey, String lockValue);
}
