package com.lynjava.ddd.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private TestFilter testFilter;

    @Bean
    public FilterRegistrationBean registFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(testFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("TestFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
