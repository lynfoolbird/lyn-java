package com.lynjava.ddd.persistence.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MetaObjectHandler dddMetaObjectHandler() {
        return new DddMetaObjectHandler();
    }

    @Bean
    public ISqlInjector dddSqlInjector() {
        return new DddSqlInjector();
    }
}
