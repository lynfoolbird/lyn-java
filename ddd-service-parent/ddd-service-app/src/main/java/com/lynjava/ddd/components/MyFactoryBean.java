package com.lynjava.ddd.components;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean {

    public void print() {
        System.out.println("test print lee");
    }

    @Override
    public Object getObject() throws Exception {
        System.out.println("test myfactory bean");
        return new Object();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
