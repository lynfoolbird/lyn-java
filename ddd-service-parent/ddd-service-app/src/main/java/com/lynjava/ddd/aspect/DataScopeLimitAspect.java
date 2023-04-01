package com.lynjava.ddd.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 横向越权校验
 * 资源id用spel表达式取值，支持list形式
 *
 * @author li
 */
@Aspect
@Component
public class DataScopeLimitAspect {

    @Before(value = "@annotation(com.lynjava.ddd.common.annotation.DataScopeLimit)")
    public void before(JoinPoint joinPoint) {

    }
}
