package com.lynjava.ddd.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class ExceptionUtils {

    public static <T,R> R ignoreException(T t, Function<T, R> function) {
        try {
            return function.apply(t);
        } catch (Throwable e) {
        }
        return null;
    }

    public static <T,R> R justLog(T t, Function<T, R> function) {
        try {
            return function.apply(t);
        } catch (Throwable e) {
            log.error("do something error:", e);
        }
        return null;
    }
}
