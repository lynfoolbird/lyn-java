package com.lynjava.rpc.config;

import com.lynjava.rpc.client.LynRpcClientProcessor;
import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.client.balancer.LoadBalancerFactory;
import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.client.discovery.ZkServiceSubscribe;
import com.lynjava.rpc.client.proxy.ClientStubProxyFactory;
import com.lynjava.rpc.core.consts.RpcConstants;
import com.lynjava.rpc.server.LynRpcServerProcessor;
import com.lynjava.rpc.server.register.IServiceRegister;
import com.lynjava.rpc.server.register.ZkServiceRegister;
import com.lynjava.rpc.server.transport.NettyRpcServer;
import com.lynjava.rpc.server.transport.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author li
 */
@Configuration
@EnableConfigurationProperties({LynRpcProperties.class})
public class LynRpcAutoConfiguration {
    @Bean
    public LynRpcProperties rpcProperties(Environment environment) {
        BindResult<LynRpcProperties> result = Binder.get(environment).bind(RpcConstants.CONFIG_PREFIX, LynRpcProperties.class);
        return result.get();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientStubProxyFactory clientStubProxyFactory() {
        return new ClientStubProxyFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer loadBalancer(@Autowired LynRpcProperties rpcProperties) {
        // 支持SPI扩展
        return LoadBalancerFactory.getLoadBalancer(rpcProperties.getBalancer());
    }

    @Bean
    @ConditionalOnMissingBean
    public IServiceSubscribe serviceSubscribe(@Autowired LynRpcProperties rpcProperties,
                                              @Autowired ILoadBalancer loadBalancer) {
        return new ZkServiceSubscribe(rpcProperties.getRegisterAddress(), loadBalancer);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "client.enable", havingValue = "true", matchIfMissing = true)
    public LynRpcClientProcessor lynRpcClientProcessor(@Autowired LynRpcProperties rpcProperties,
                                                       @Autowired IServiceSubscribe serviceSubscribe,
                                                       @Autowired ClientStubProxyFactory clientStubProxyFactory) {
        return new LynRpcClientProcessor(rpcProperties, serviceSubscribe, clientStubProxyFactory);
    }
    @Bean
    @ConditionalOnMissingBean
    public IServiceRegister serviceRegister(@Autowired LynRpcProperties rpcProperties) {
        return new ZkServiceRegister(rpcProperties.getRegisterAddress());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    public RpcServer rpcServer(@Autowired LynRpcProperties rpcProperties) {
        return new NettyRpcServer(rpcProperties.getPort());
    }

    @Bean
    @ConditionalOnMissingBean(LynRpcServerProcessor.class)
    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "server.enable", havingValue = "true", matchIfMissing = true)
    public LynRpcServerProcessor lynRpcServerProcessor(@Autowired LynRpcProperties rpcProperties,
                                                       @Autowired IServiceRegister serviceRegister,
                                                       @Autowired RpcServer rpcServer) {
        return new LynRpcServerProcessor(rpcProperties, serviceRegister, rpcServer);
    }
}