package com.lynjava.rpc.config;

import com.lynjava.rpc.client.LynRpcClientProcessor;
import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.client.balancer.LoadBalancerFactory;
import com.lynjava.rpc.client.discovery.IServiceDiscovery;
import com.lynjava.rpc.client.discovery.ZkServiceDiscovery;
import com.lynjava.rpc.client.proxy.ClientStubProxyFactory;
import com.lynjava.rpc.core.consts.RpcConstants;
import com.lynjava.rpc.server.LynRpcServerProcessor;
import com.lynjava.rpc.server.register.IServiceRegister;
import com.lynjava.rpc.server.register.ZkServiceRegister;
import com.lynjava.rpc.server.transport.NettyRpcServer;
import com.lynjava.rpc.server.transport.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li
 */
@Configuration
@EnableConfigurationProperties(LynRpcConfig.class)
public class LynRpcAutoConfiguration {

    @Autowired
    private LynRpcConfig rpcConfig;

    @Bean
    @ConditionalOnMissingBean
    public ClientStubProxyFactory clientStubProxyFactory() {
        return new ClientStubProxyFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer loadBalancer(@Autowired LynRpcConfig rpcConfig) {
        // 支持SPI扩展
        return LoadBalancerFactory.getLoadBalancer(rpcConfig.getBalancer());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({LynRpcConfig.class, ILoadBalancer.class})
    public IServiceDiscovery serviceDiscovery(@Autowired LynRpcConfig rpcConfig,
                                              @Autowired ILoadBalancer loadBalancer) {
        return new ZkServiceDiscovery(rpcConfig.getRegisterAddress(), loadBalancer);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "client.enable", havingValue = "true", matchIfMissing = true)
    public LynRpcClientProcessor lynRpcClientProcessor(@Autowired LynRpcConfig rpcConfig,
                                                       @Autowired IServiceDiscovery serviceDiscovery,
                                                       @Autowired ClientStubProxyFactory clientStubProxyFactory) {
        return new LynRpcClientProcessor(rpcConfig, serviceDiscovery, clientStubProxyFactory);
    }
    @Bean
    @ConditionalOnMissingBean
    public IServiceRegister serviceRegister() {
        return new ZkServiceRegister(rpcConfig.getRegisterAddress());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    public RpcServer rpcServer(@Autowired LynRpcConfig rpcConfig) {
        return new NettyRpcServer(rpcConfig.getPort());
    }

    @Bean
    @ConditionalOnMissingBean(LynRpcServerProcessor.class)
    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "server.enable", havingValue = "true", matchIfMissing = true)
    public LynRpcServerProcessor lynRpcServerProcessor(@Autowired LynRpcConfig rpcConfig,
                                                       @Autowired IServiceRegister serviceRegister,
                                                       @Autowired RpcServer rpcServer) {
        return new LynRpcServerProcessor(rpcConfig, serviceRegister, rpcServer);
    }
}