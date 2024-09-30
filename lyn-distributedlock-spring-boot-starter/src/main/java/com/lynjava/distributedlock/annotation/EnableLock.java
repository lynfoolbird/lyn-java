package com.lynjava.distributedlock.annotation;

import com.lynjava.distributedlock.config.LockConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable开关demo
 * 复合注解，导入外部配置
 *
 * @author li
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LockConfiguration.class)
public @interface EnableLock {
}
