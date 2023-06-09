package com.lynjava.rpc.config;

import com.lynjava.rpc.server.LynRpcServerProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LynRpcAutoConfiguration {

    @Bean
    public LynRpcServerProcessor lynRpcProcessor() {
        return new LynRpcServerProcessor();
    }
}
