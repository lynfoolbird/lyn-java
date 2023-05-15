package com.lynjava.limiter.config;

import com.lynjava.limiter.manager.ILockService;
import com.lynjava.limiter.manager.impl.DbLockServiceImpl;
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
    @ConditionalOnProperty(name = "lock.type", havingValue = "db")
    public ILockService dbLockService() {
        return new DbLockServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "lock.type", havingValue = "redis")
    public ILockService redisLockService() {
        return new DbLockServiceImpl();
    }
}
