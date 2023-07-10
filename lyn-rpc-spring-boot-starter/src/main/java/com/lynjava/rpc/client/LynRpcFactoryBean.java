package com.lynjava.rpc.client;

import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.client.proxy.ClientStubInvocationHandler;
import com.lynjava.rpc.config.LynRpcProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

public class LynRpcFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clazz;

    private String version;

    @Autowired
    private IServiceSubscribe serviceSubscribe;

    @Autowired
    private LynRpcProperties rpcProperties;

    public LynRpcFactoryBean(Class<T> clazz, String version) {
        this.clazz = clazz;
        this.version = version;
    }
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(this.clazz.getClassLoader(), new Class[]{clazz},
                new ClientStubInvocationHandler(serviceSubscribe, rpcProperties, clazz, version));
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
