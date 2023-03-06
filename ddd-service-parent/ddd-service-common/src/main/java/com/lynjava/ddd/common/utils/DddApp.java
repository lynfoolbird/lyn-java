package com.lynjava.ddd.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * 应用上下文工具类
 *
 * @author li
 */
public final class DddApp {

    private static ApplicationContext context;
    private DddApp() {
    }

    public static void setContext(ApplicationContext context) {
        DddApp.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
