package com.lynjava.rpc.client.proxy;

import com.lynjava.rpc.client.discovery.IServiceSubscribe;
import com.lynjava.rpc.client.transport.NetClientTransportFactory;
import com.lynjava.rpc.client.transport.RequestMetadata;
import com.lynjava.rpc.config.LynRpcProperties;
import com.lynjava.rpc.core.consts.MsgStatusEnum;
import com.lynjava.rpc.core.exception.RpcException;
import com.lynjava.rpc.core.model.RpcRequest;
import com.lynjava.rpc.core.model.RpcResponse;
import com.lynjava.rpc.core.model.ServiceInfo;
import com.lynjava.rpc.core.protocol.MessageHeader;
import com.lynjava.rpc.core.protocol.MessageProtocol;
import com.lynjava.rpc.core.util.RpcUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;

@Slf4j
public class ClientStubInvocationHandler implements InvocationHandler {

    private IServiceSubscribe serviceSubscribe;

    private LynRpcProperties rpcProperties;

    private Class<?> clazz;

    private String version;

    public ClientStubInvocationHandler(IServiceSubscribe serviceSubscribe, LynRpcProperties rpcProperties,
                                       Class<?> clazz, String version) {
        super();
        this.clazz = clazz;
        this.version = version;
        this.serviceSubscribe = serviceSubscribe;
        this.rpcProperties = rpcProperties;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceKey = RpcUtils.serviceKey(rpcProperties.getAppName(), this.clazz.getName(), this.version, rpcProperties.getUsf());
        // 1、获得服务信息  --无法获取到注册信息 TODO
        ServiceInfo serviceInfo = serviceSubscribe.discovery(serviceKey);
        if (serviceInfo == null) {
            throw new RpcException("404");
        }
//        ServiceInfo serviceInfo = ServiceInfo.builder()
//                .appName("ddd_service")
//                .address("192.168.3.5")
//                .port(9999)
//                .serviceName("com.lynjava.ddd.common.other.ILynRpcDemoService")
//                .version("1.0")
//                .usf("lyn-dev")
//                .build();
        // 2、封装请求协议
        RpcRequest request = RpcRequest.builder()
                .serviceKey(serviceKey)
                .method(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        MessageProtocol<RpcRequest> messageProtocol = MessageProtocol.<RpcRequest>builder()
                // 请求体
                .header(MessageHeader.build(rpcProperties.getSerializer()))
                // 请求体
                .body(request)
                .build();
        // 3、发送网络请求
        MessageProtocol<RpcResponse> responseMessageProtocol = NetClientTransportFactory
                .getNetClientTransport()
                .sendRequest(RequestMetadata.builder()
                        .protocol(messageProtocol)
                        .address(serviceInfo.getAddress())
                        .port(serviceInfo.getPort())
                        .timeout(rpcProperties.getTimeout())
                        .build());
        // 4、处理响应结果
        if (responseMessageProtocol == null) {
            log.error("请求超时");
            throw new RpcException("rpc调用结果失败， 请求超时 timeout:" + rpcProperties.getTimeout());
        }

        if (!MsgStatusEnum.isSuccess(responseMessageProtocol.getHeader().getStatus())) {
            log.error("rpc调用结果失败， message：{}", responseMessageProtocol.getBody().getMessage());
            throw new RpcException(responseMessageProtocol.getBody().getMessage());
        }
        return responseMessageProtocol.getBody().getData();
    }
}
