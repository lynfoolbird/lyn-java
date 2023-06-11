package com.lynjava.rpc.client.proxy;

import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.config.LynRpcConfig;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ClientStubProxyFactory {

    private Map<Class<?>, Object> objectCache = new HashMap<>();

    /**
     * 获取代理对象
     *
     * @param clazz   接口
     * @param version 服务版本
     * @param <T>
     * @return 代理对象
     */
    public <T> T getProxy(Class<T> clazz, String version, IServiceSubscribe serviceSubscribe, LynRpcConfig rpcConfig) {
        return (T) objectCache.computeIfAbsent(clazz, clz ->
                Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new ClientStubInvocationHandler(serviceSubscribe, rpcConfig,
                        clz, version))
        );
    }
}
