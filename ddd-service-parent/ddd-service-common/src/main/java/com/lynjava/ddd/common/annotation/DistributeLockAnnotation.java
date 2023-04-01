package com.lynjava.ddd.common.annotation;

import com.lynjava.ddd.common.consts.RedisLockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁：支持不同实现
 *
 * @author li
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DistributeLockAnnotation {
    // 特定参数识别，默认取第 0 个下标
    int lockFiled() default 0;

    // 超时重试次数
    int tryCount() default 3;

    // 自定义加锁类型
    RedisLockTypeEnum typeEnum();

    // 等待超时时间，单位秒
    long waitTimeout() default 3;

    // 锁定超时时间，单位为秒
    long lockTimeout() default 10;
}
