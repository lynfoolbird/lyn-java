package com.lynjava.distributedlock.api;

/**
 * 支持独占锁、可重入锁；支持自动续期；支持防死锁；支持tryLock
 */
public interface IDistributedLock {
    boolean acquireExclusiveLock(String lockName);

    boolean releaseExclusiveLock(String lockName);

    boolean acquireReentrantLock(String lockName, String holder, String additional);

    boolean releaseReentrantLock(String lockName, String holder);

    default boolean isAllowCleanUp(String lockName, String holder, String additional) {
        return false;
    }
}
