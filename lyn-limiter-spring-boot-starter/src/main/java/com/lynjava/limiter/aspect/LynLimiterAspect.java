package com.lynjava.limiter.aspect;

import com.lynjava.limiter.annotation.LynLimit;
import com.lynjava.limiter.common.Limiter;
import com.lynjava.limiter.conditon.LimitAspectCondition;
import com.lynjava.limiter.manager.ILynLimiter;
import com.lynjava.limiter.manager.impl.GuavaLimiter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Conditional;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
//@Conditional(LimitAspectCondition.class)
public class LynLimiterAspect {
//    @Setter(onMethod_ = @Autowired)
    @Autowired(required = false)
    private ILynLimiter limiterManager;

    @Before("@annotation(com.lynjava.limiter.annotation.LynLimit)")
    public void before(JoinPoint joinPoint) {
        System.out.println("LynLimiterAspect begin..");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("LynLimiterAspect method is " + method.getName());
        LynLimit limit = method.getAnnotation(LynLimit.class);
        Limiter limiter = Limiter.builder()
                .permitsPerSecond(limit.permitsPerSecond())
                .key(limit.key())
                .build();
        System.out.println("LynLimiterAspect limiter is " + limiter);
        if (Objects.isNull(limiterManager)) {
            System.out.println("LynLimiterAspect, no limiter.");
            return;
        }
        if(!limiterManager.tryAcquire(limiter)){
            throw new RuntimeException( "There are currently many people , please try again later!" );
        }
    }
}
