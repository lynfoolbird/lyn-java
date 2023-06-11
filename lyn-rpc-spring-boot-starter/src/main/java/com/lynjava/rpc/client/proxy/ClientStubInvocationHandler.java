package com.lynjava.rpc.client.proxy;

import com.lynjava.rpc.client.discovery.IServiceSubscribe;
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

    private IServiceSubscribe serviceSubscribe;

    private LynRpcConfig rpcConfig;

    private Class<?> clazz;

    private String version;

    public ClientStubInvocationHandler(IServiceSubscribe serviceSubscribe, LynRpcConfig rpcConfig,
                                       Class<?> clazz, String version) {
        super();
        this.clazz = clazz;
        this.version = version;
        this.serviceSubscribe = serviceSubscribe;
        this.rpcConfig = rpcConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceKey = RpcUtils.serviceKey(rpcConfig.getAppName(), this.clazz.getName(), this.version, rpcConfig.getUsf());
        // 1、获得服务信息
        ServiceInfo serviceInfo = serviceSubscribe.discovery(serviceKey);
        if (serviceInfo == null) {
            throw new RpcException("404");
        }
        // 2、封装请求协议
        RpcRequest request = RpcRequest.builder()
                .serviceKey(serviceKey)
                .method(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        MessageProtocol<RpcRequest> messageProtocol = MessageProtocol.<RpcRequest>builder()
                // 请求体
                .header(MessageHeader.build(rpcConfig.getSerializer()))
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
                        .timeout(rpcConfig.getTimeout())
                        .build());
        // 4、处理响应结果
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
