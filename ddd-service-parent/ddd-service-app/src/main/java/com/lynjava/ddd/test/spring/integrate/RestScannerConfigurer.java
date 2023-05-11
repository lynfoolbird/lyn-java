package com.lynjava.ddd.test.spring.integrate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

@Component
public class RestScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

    /**
     * 扫描指定包，按过滤器过滤出指定类，
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        RestClientScanner scanner = new RestClientScanner(beanDefinitionRegistry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestClient.class));
        scanner.scan("com.lynjava.ddd.test.spring.integrate.external");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
