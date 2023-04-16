package com.lynjava.limiter.manager.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.lynjava.limiter.common.Limiter;
import com.lynjava.limiter.manager.ILynLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于guava的单机限流 令牌桶算法
 */
public class GuavaLimiter implements ILynLimiter {
    private final static Map<String, RateLimiter> RATE_LIMITER_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean tryAcquire(Limiter limiter) {
        System.out.println("GuavaLimiter tryAcquire key is " + limiter.getKey());
        String key = limiter.getKey();
        if (!RATE_LIMITER_MAP.containsKey(key)) {
            RateLimiter rateLimiter = RateLimiter.create(limiter.getPermitsPerSecond());
            RATE_LIMITER_MAP.put(key, rateLimiter);
            return rateLimiter.tryAcquire();
        }
        return RATE_LIMITER_MAP.get(key).tryAcquire();
    }
}
