package com.lynjava.rpc.config;

import com.lynjava.rpc.server.LynRpcProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LynRpcAutoConfigure {

    @Bean
    public LynRpcProcessor lynRpcProcessor() {
        return new LynRpcProcessor();
    }
}
