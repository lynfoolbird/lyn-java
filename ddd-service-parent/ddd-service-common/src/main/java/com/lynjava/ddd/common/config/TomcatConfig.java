package com.lynjava.ddd.common.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@SuppressWarnings("rawtypes")
@Configuration
public class TomcatConfig {

    @Bean
    public ServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean dispatchBean = new ServletRegistrationBean(dispatcherServlet, "/ddd_service/*");
        dispatchBean.setOrder(0);
        return dispatchBean;
    }

    @Bean
    public MyServlet myServlet() {
        return new MyServlet();
    }

    // TODO 好像不注册也能接入到MyServlet？？
    @Bean
    public ServletRegistrationBean myServletRegistrationBean(MyServlet myServlet) {
        ServletRegistrationBean dispatchBean = new ServletRegistrationBean(myServlet, "/");
        dispatchBean.setOrder(1);
        return dispatchBean;
    }

}
