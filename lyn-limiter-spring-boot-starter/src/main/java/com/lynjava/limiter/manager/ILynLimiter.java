package com.lynjava.limiter.manager;

import com.lynjava.limiter.common.Limiter;

public interface ILynLimiter {
    boolean tryAcquire(Limiter limiter);
}
