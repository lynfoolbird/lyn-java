package com.lynjava.ddd.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据横向越权：资源id，资源类型，读写权限
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScopeLimit {


    // 资源类型，默认应用ID
    String resourceType() default "APPID";
}
