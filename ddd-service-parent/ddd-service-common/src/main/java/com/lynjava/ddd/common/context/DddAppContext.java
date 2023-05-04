package com.lynjava.ddd.common.context;

import org.springframework.context.ApplicationContext;

/**
 * 应用容器上下文工具类
 *
 * @author li
 */
public final class DddAppContext {

    private static ApplicationContext context;
    private DddAppContext() {
    }

    public static void setContext(ApplicationContext context) {
        DddAppContext.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
