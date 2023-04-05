package com.outter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认只扫描启动类所在包及子包下面的类
 * 外部的配置可用Import注解导入
 *
 * @author li
 */
@Configuration
@ConditionalOnProperty(prefix = "lock", name = "enable", havingValue = "true")
public class LockConfiguration {

    @Bean
    public LockService lockService() {
        return new LockService();
    }
}


