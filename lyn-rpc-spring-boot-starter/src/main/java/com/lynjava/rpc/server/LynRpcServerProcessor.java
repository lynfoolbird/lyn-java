package com.lynjava.rpc.server;

import com.lynjava.rpc.config.LynRpcConfig;
import com.lynjava.rpc.core.annotation.LynRpcService;
import com.lynjava.rpc.core.model.ServiceInfo;
import com.lynjava.rpc.core.util.RpcUtils;
import com.lynjava.rpc.server.cache.LocalServerCache;
import com.lynjava.rpc.server.register.IServiceRegister;
import com.lynjava.rpc.server.transport.RpcServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class LynRpcServerProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private LynRpcConfig rpcConfig;
    private IServiceRegister serviceRegister;
    private RpcServer rpcServer;

    public LynRpcServerProcessor(LynRpcConfig rpcConfig,
                                 IServiceRegister serviceRegister,
                                 RpcServer rpcServer) {
        this.rpcConfig = rpcConfig;
        this.serviceRegister = serviceRegister;
        this.rpcServer = rpcServer;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Spring启动完毕过后会收到一个事件通知
        if (Objects.isNull(event.getApplicationContext().getParent())){
            ApplicationContext context = event.getApplicationContext();
            // 服务注册
            boolean needStartServer = registerService(context);
            // 启动Netty Server
            if (needStartServer) {
                new Thread(() -> rpcServer.start()).start();
                log.info(" rpc server :{} start, appName :{} , port :{}", rpcServer, rpcConfig.getAppName(), rpcConfig.getPort());
            }
        }
    }

    private boolean registerService(ApplicationContext context) {
        Map<String, Object> beans = context.getBeansWithAnnotation(LynRpcService.class);
        if (beans.size() == 0) {
            return false;
        }
        for(Object bean : beans.values()) {
            LynRpcService rpcService = bean.getClass().getAnnotation(LynRpcService.class);
            if (Objects.isNull(rpcService)) {
                continue;
            }
            try {
                String serviceName = rpcService.interfaceType().getName();
                String version = rpcService.version();
                String serviceKey = RpcUtils.serviceKey(rpcConfig.getAppName(), serviceName, version, rpcConfig.getUsf());
                // 服务端缓存服务与bean映射关系
                LocalServerCache.store(serviceKey, bean);
                // 服务注册
                ServiceInfo serviceInfo = ServiceInfo.builder()
                        .appName(rpcConfig.getAppName())
                        .address(InetAddress.getLocalHost().getHostAddress())
                        .port(rpcConfig.getPort())
                        .serviceName(serviceName)
                        .version(version)
                        .usf(rpcConfig.getUsf())
                        .build();
                serviceRegister.register(serviceInfo);
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        // 关闭之后把服务从ZK上清除
                        serviceRegister.destroy();
                    }catch (Exception ex){
                        log.error("", ex);
                    }
                }));
            } catch (Exception ex) {
                log.error("服务注册出错:{}", ex);
            }
        }
        return true;
    }
}