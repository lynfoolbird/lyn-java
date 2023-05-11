package com.lynjava.ddd.common.annotation;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Profile({"!blue"})
public @interface RedProfile {
}
