package com.outter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认只扫描启动类所在包及子包下面的类
 * 外部的配置可同Import注解导入
 * @author li
 */
@Configuration
public class LockConfiguration {

    @Bean
    public LockService lockService() {
        return new LockService();
    }
}


