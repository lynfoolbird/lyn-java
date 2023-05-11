package com.lynjava.ddd.test.spring.integrate;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;

public class RestClientScanner extends ClassPathBeanDefinitionScanner {
    public RestClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * 覆写方法，修改BeanDefinition
     * @param beanDefinition
     * @param beanName
     */
    @Override
    protected void postProcessBeanDefinition(AbstractBeanDefinition beanDefinition, String beanName) {
        String beanClassName = beanDefinition.getBeanClassName();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
        beanDefinition.setBeanClass(RestClientFactoryBean.class);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }


    // 支持接口
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface() && metadata.isIndependent();
    }
}
