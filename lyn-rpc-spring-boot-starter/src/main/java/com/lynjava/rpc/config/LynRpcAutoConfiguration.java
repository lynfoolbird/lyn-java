package com.lynjava.rpc.config;

import com.lynjava.rpc.client.LynRpcClientProcessor;
import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.client.balancer.LoadBalancerFactory;
import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.client.discovery.ZkServiceSubscribe;
import com.lynjava.rpc.client.proxy.ClientStubProxyFactory;
import com.lynjava.rpc.condition.EnableRpcClientCondition;
import com.lynjava.rpc.condition.EnableRpcServerCondition;
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
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author li
 */
@Configuration
@EnableConfigurationProperties({LynRpcProperties.class})
public class LynRpcAutoConfiguration {
    // =====================公共配置 begin=========================

    @Bean
    public LynRpcProperties rpcProperties(Environment environment) {
        BindResult<LynRpcProperties> result = Binder.get(environment).bind(RpcConstants.CONFIG_PREFIX, LynRpcProperties.class);
        return result.get();
    }

    // =====================客户端配置 begin=========================

    @Bean
    @ConditionalOnMissingBean
    @Conditional(EnableRpcClientCondition.class)
    public ClientStubProxyFactory clientStubProxyFactory() {
        return new ClientStubProxyFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    @Conditional(EnableRpcClientCondition.class)
    public ILoadBalancer loadBalancer(LynRpcProperties rpcProperties) {
        // 支持SPI扩展
        return LoadBalancerFactory.getLoadBalancer(rpcProperties.getBalancer());
    }

    @Bean
    @ConditionalOnMissingBean
    @Conditional(EnableRpcClientCondition.class)
    public IServiceSubscribe serviceSubscribe(@Autowired LynRpcProperties rpcProperties,
                                              @Autowired ILoadBalancer loadBalancer) {
        return new ZkServiceSubscribe(rpcProperties.getRegisterAddress(), loadBalancer);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "enable", havingValue = "client", matchIfMissing = false)
    public LynRpcClientProcessor lynRpcClientProcessor(@Autowired LynRpcProperties rpcProperties,
                                                       @Autowired IServiceSubscribe serviceSubscribe,
                                                       @Autowired ClientStubProxyFactory clientStubProxyFactory) {
        return new LynRpcClientProcessor(rpcProperties, serviceSubscribe, clientStubProxyFactory);
    }

    // =====================服务端配置 begin=========================

    @Bean
    @ConditionalOnMissingBean
    @Conditional(EnableRpcServerCondition.class)
    public IServiceRegister serviceRegister(LynRpcProperties rpcProperties) {
        return new ZkServiceRegister(rpcProperties.getRegisterAddress());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    @Conditional(EnableRpcServerCondition.class)
    public RpcServer rpcServer(LynRpcProperties rpcProperties) {
        return new NettyRpcServer(rpcProperties.getPort());
    }

    @Bean
    @ConditionalOnMissingBean(LynRpcServerProcessor.class)
    @Conditional(EnableRpcServerCondition.class)
//    @ConditionalOnProperty(prefix = RpcConstants.CONFIG_PREFIX, name = "enable", havingValue = "server", matchIfMissing = true)
    public LynRpcServerProcessor lynRpcServerProcessor(@Autowired LynRpcProperties rpcProperties,
                                                       @Autowired IServiceRegister serviceRegister,
                                                       @Autowired RpcServer rpcServer) {
        return new LynRpcServerProcessor(rpcProperties, serviceRegister, rpcServer);
    }
}