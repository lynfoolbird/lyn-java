package com.lynjava.limiter.config;

import com.lynjava.limiter.manager.ILynLimiter;
import com.lynjava.limiter.manager.impl.GuavaLimiter;
import com.lynjava.limiter.manager.impl.RedisLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LynLimiterAutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "limit.type", havingValue = "local")
    public ILynLimiter guavaLimiter(){
        return new GuavaLimiter();
    }

    @Bean
    @ConditionalOnProperty(name = "limit.type", havingValue = "redis")
    public ILynLimiter redisLimiter(){
        return new RedisLimiter();
    }
}
