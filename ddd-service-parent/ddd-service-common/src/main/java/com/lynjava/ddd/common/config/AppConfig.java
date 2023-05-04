package com.lynjava.ddd.common.config;

import com.lynjava.ddd.common.context.DddAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DddAppContext.setContext(applicationContext);
    }
}
