package com.lynjava.rpc.client;

import com.lynjava.rpc.core.annotation.LynRpcAutowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

@Slf4j
public class LynRpcClientV2Processor implements BeanFactoryPostProcessor {
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
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(field.getType());
                    builder.addConstructorArgValue(field.getType());
                    builder.addConstructorArgValue(rpcAutowired.version());
                    AbstractBeanDefinition abd = builder.getBeanDefinition();
                    abd.setBeanClass(LynRpcFactoryBean.class);
                    ((BeanDefinitionRegistry)beanFactory).registerBeanDefinition(field.getName(), abd);
                }
            });
        }
    }
}