package com.lynjava.ddd.test.spring;

import com.lynjava.ddd.test.common.ITestPrinter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class TestFactoryBean implements FactoryBean {
    private Class clazz = ITestPrinter.class;
    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
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
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
