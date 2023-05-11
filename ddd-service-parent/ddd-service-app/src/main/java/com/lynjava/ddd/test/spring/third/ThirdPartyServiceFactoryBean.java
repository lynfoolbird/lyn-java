package com.lynjava.ddd.test.spring.third;

import lombok.SneakyThrows;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.classreading.MetadataReader;

import java.lang.reflect.Proxy;

public class ThirdPartyServiceFactoryBean implements FactoryBean<Object> {

    private final MetadataReader thirdPartyService;

    public ThirdPartyServiceFactoryBean(MetadataReader thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @Override
    public Object getObject() throws Exception {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(thirdPartyService.getAnnotationMetadata()
                .getAnnotationAttributes(ThirdPartyService.class.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException("未找到 ThirdPartyService 注解");
        }
        Class<ThirdPartyServiceHandler> handler = (Class<ThirdPartyServiceHandler>) attributes.getClass("handler");
        Class<?> interfaceClass = Class.forName(thirdPartyService.getClassMetadata().getClassName());
        return Proxy.newProxyInstance(ThirdPartyServiceFactoryBean.class.getClassLoader(), new Class[]{interfaceClass},
                handler.getDeclaredConstructor().newInstance());
    }

    @SneakyThrows
    @Override
    public Class<?> getObjectType() {
        return Class.forName(thirdPartyService.getClassMetadata().getClassName());
    }
}
