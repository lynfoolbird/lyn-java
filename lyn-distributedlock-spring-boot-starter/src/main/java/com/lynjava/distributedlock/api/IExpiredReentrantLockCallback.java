package com.lynjava.distributedlock.api;

/**
 *
 */
public interface IExpiredReentrantLockCallback {

    default boolean isAllowCleanUp(String lockName, String holder, String additional) {
        return false;
    }
}
