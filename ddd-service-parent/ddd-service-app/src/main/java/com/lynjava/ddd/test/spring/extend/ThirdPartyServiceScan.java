package com.lynjava.ddd.test.spring.extend;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ThirdPartyServiceRegistrar.class)
public @interface ThirdPartyServiceScan {
    /**
     * 用于配置扫描第三方服务的包路径，如果不配置默认扫描标注的类所在的包和子包
     */
    String[] value() default {};
}
