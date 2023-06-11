package com.lynjava.rpc.client;

import com.lynjava.rpc.client.discovery.IServiceDiscovery;
import com.lynjava.rpc.client.proxy.ClientStubProxyFactory;
import com.lynjava.rpc.config.LynRpcConfig;
import com.lynjava.rpc.core.annotation.LynRpcAutowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

@Slf4j
public class LynRpcClientProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private LynRpcConfig rpcConfig;
    private IServiceDiscovery serviceDiscovery;
    private ClientStubProxyFactory clientStubProxyFactory;

    private ApplicationContext applicationContext;

    public LynRpcClientProcessor(LynRpcConfig rpcConfig,
                                 IServiceDiscovery serviceDiscovery,
                                 ClientStubProxyFactory clientStubProxyFactory) {
        this.rpcConfig = rpcConfig;
        this.serviceDiscovery = serviceDiscovery;
        this.clientStubProxyFactory = clientStubProxyFactory;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (Objects.isNull(beanClassName)) {
                continue;
            }
            Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.getClass().getClassLoader());
            ReflectionUtils.doWithFields(clazz, field -> {
                LynRpcAutowired rpcAutowired = AnnotationUtils.getAnnotation(field, LynRpcAutowired.class);
                if (rpcAutowired != null) {
                    Object bean = applicationContext.getBean(clazz);
                    field.setAccessible(true);
                    // 修改为代理对象
                    ReflectionUtils.setField(field, bean, clientStubProxyFactory.getProxy(field.getType(), rpcAutowired.version(), serviceDiscovery, rpcConfig));
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}