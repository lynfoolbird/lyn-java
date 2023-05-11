package com.lynjava.ddd.test.spring.third;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThirdPartyService {
    /**
     * 第三方服务处理器
     */
    Class<? extends ThirdPartyServiceHandler> handler();
}
