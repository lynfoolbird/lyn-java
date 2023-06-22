package com.lynjava.ddd.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("lynTestBeanFactoryPostProcessor:");
        TestBeanFactoryPostProcessor bean = beanFactory.getBean(TestBeanFactoryPostProcessor.class);
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
    }
}
