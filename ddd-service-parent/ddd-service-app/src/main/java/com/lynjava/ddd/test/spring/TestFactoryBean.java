package com.lynjava.ddd.test.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class TestFactoryBean<T> implements FactoryBean<T> {
    private Class<T> mapperInterfaceClazz;
    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(mapperInterfaceClazz.getClassLoader(),
                new Class[]{mapperInterfaceClazz},
                (Object proxy, Method method, Object[] args) -> {
                    System.out.println("proxy pre handler...");
                    System.out.println("method name is " + method.getName());
                    System.out.println("execute target method. params is " + ArrayUtils.toString(args));
                    System.out.println("proxy post handler...");
                    return "ok";
        });
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterfaceClazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
