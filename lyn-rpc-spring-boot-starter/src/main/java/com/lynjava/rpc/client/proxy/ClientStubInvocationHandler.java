package com.lynjava.rpc.client.proxy;

import com.lynjava.rpc.client.discovery.IServiceDiscovery;
import com.lynjava.rpc.client.transport.NetClientTransportFactory;
import com.lynjava.rpc.client.transport.RequestMetadata;
import com.lynjava.rpc.config.LynRpcConfig;
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

@Slf4j
public class ClientStubInvocationHandler implements InvocationHandler {

    private IServiceDiscovery serviceDiscovery;

    private LynRpcConfig rpcConfig;

    private Class<?> clazz;

    private String version;

    public ClientStubInvocationHandler(IServiceDiscovery serviceDiscovery, LynRpcConfig rpcConfig,
                                       Class<?> clazz, String version) {
        super();
        this.clazz = clazz;
        this.version = version;
        this.serviceDiscovery = serviceDiscovery;
        this.rpcConfig = rpcConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1、获得服务信息
        ServiceInfo serviceInfo = serviceDiscovery.discovery(RpcUtils.serviceKey(this.clazz.getName(), this.version));
        if (serviceInfo == null) {
            throw new RpcException("404");
        }

        MessageProtocol<RpcRequest> messageProtocol = new MessageProtocol<>();
        // 设置请求头
        messageProtocol.setHeader(MessageHeader.build(rpcConfig.getSerializer()));
        // 设置请求体
        RpcRequest request = new RpcRequest();
        request.setServiceName(RpcUtils.serviceKey(this.clazz.getName(), this.version));
        request.setMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        messageProtocol.setBody(request);

        // 发送网络请求 拿到结果
        MessageProtocol<RpcResponse> responseMessageProtocol = NetClientTransportFactory.getNetClientTransport()
                .sendRequest(RequestMetadata.builder().protocol(messageProtocol).address(serviceInfo.getAddress())
                        .port(serviceInfo.getPort()).timeout(rpcConfig.getTimeout()).build());

        if (responseMessageProtocol == null) {
            log.error("请求超时");
            throw new RpcException("rpc调用结果失败， 请求超时 timeout:" + rpcConfig.getTimeout());
        }

        if (!MsgStatusEnum.isSuccess(responseMessageProtocol.getHeader().getStatus())) {
            log.error("rpc调用结果失败， message：{}", responseMessageProtocol.getBody().getMessage());
            throw new RpcException(responseMessageProtocol.getBody().getMessage());
        }
        return responseMessageProtocol.getBody().getData();
    }
}
