package com.lynjava.rpc.core.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface LynRpcService {

    /**
     *  暴露服务接口类型
     */
    Class<?> interfaceType() default Object.class;

    /**
     *  服务版本
     */
    String version() default "1.0";
}
