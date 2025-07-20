package com.lynjava.ddd.common.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * @author 李亚南
 */
public class SpringUtils implements ApplicationContextInitializer {

    private static ApplicationContext applicationContext;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public static <T> T registerBean(ConfigurableApplicationContext context, String name, Class<T> clazz, Object...args) {
        if (context.containsBean(name)) {
            Object bean = context.getBean(name);
            if (bean.getClass().isAssignableFrom(clazz)) {
                return (T) bean;
            }
            throw new RuntimeException("Bean has already exists: " + name);
        } else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
            if (!registry.containsBeanDefinition(name)) {
                registry.registerBeanDefinition(name, beanDefinition);
            }
            return context.getBean(name, clazz);
        }
    }
}
