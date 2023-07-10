package com.lynjava.rpc.client;

import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.client.proxy.ClientStubProxyFactory;
import com.lynjava.rpc.config.LynRpcProperties;
import com.lynjava.rpc.core.annotation.LynRpcAutowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

/**
 * 不奏效，无法和其他bean同时注入，待研究
 */
@Slf4j
public class LynRpcClientProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private LynRpcProperties rpcProperties;
    private IServiceSubscribe serviceSubscribe;
    private ClientStubProxyFactory clientStubProxyFactory;

    public LynRpcClientProcessor(LynRpcProperties rpcProperties,
                                 IServiceSubscribe serviceSubscribe,
                                 ClientStubProxyFactory clientStubProxyFactory) {
        this.rpcProperties = rpcProperties;
        this.serviceSubscribe = serviceSubscribe;
        this.clientStubProxyFactory = clientStubProxyFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Spring启动完毕过后会收到一个事件通知
        if (Objects.isNull(event.getApplicationContext().getParent())){
            ApplicationContext context = event.getApplicationContext();
            for (String beanDefinitionName : context.getBeanDefinitionNames()) {
                Class clazz = context.getType(beanDefinitionName);
                ReflectionUtils.doWithFields(clazz, field -> {
                    LynRpcAutowired rpcAutowired = AnnotationUtils.getAnnotation(field, LynRpcAutowired.class);
                    if (rpcAutowired != null) {
                        Object bean = context.getBean(clazz);
                        field.setAccessible(true);
                        Object proxyObj = clientStubProxyFactory
                                .getProxy(field.getType(), rpcAutowired.version(), serviceSubscribe, rpcProperties);
                        // 修改为代理对象
                        ReflectionUtils.setField(field, bean, proxyObj);
                        System.out.println(bean);
                    }
                });
            }
        }
    }
}