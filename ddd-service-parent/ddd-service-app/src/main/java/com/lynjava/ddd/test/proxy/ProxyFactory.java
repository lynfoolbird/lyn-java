package com.lynjava.ddd.test.proxy;


import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static  <T> T newInstance(Class<T> clazz, Session session) {
        TestProxy<T> testProxy = new TestProxy(clazz, session, "SELECT");
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},testProxy);
    }

    public static Object newInstance(Object obj, Session session) {
        TestProxy testProxy = new TestProxy(obj, session, "SELECT");
        return  Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),testProxy);
    }
}
