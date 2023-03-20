package com.lynjava.ddd.test.extension.proxy;


import java.lang.reflect.Proxy;

/**
 * 代理工厂
 *
 * @author li
 */
public class ProxyFactory {
    /**
     * 接口代理
     *
     * @param clazz
     * @param session
     * @return T 代理对象实例
     * @param <T>
     */
    public static  <T> T newInstance(Class<T> clazz, Session session) {
        TestInvocationHandler<Class<T>> invocationHandler = new TestInvocationHandler<>(clazz, session);
        Object proxyObj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
        return (T) proxyObj ;
    }

    /**
     * 实现类代理
     * @param obj
     * @param session
     * @return Object 代理对象实例
     */
    public static Object newInstance(Object obj, Session session) {
        TestInvocationHandler<Object> invocationHandler = new TestInvocationHandler<>(obj, session);
        return  Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), invocationHandler);
    }
}
