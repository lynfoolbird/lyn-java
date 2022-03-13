package com.lynjava.ddd.test.customize.spring;

public class LynBeanDefinition {
    private Class clazz;

    private String scope;

    public LynBeanDefinition(){

    }
    public LynBeanDefinition(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
