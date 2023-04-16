package com.lynjava.limiter.manager.impl;

import com.lynjava.limiter.common.Limiter;
import com.lynjava.limiter.manager.ILynLimiter;

/**
 * 基于redis的分布式限流 lua
 */
public class RedisLimiter implements ILynLimiter {
    @Override
    public boolean tryAcquire(Limiter limiter) {
        return true;
    }
}
