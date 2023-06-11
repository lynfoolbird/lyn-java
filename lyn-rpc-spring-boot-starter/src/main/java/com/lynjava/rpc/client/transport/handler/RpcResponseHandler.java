package com.lynjava.rpc.client.transport.handler;


import com.lynjava.rpc.client.cache.LocalRpcResponseCache;
import com.lynjava.rpc.core.model.RpcResponse;
import com.lynjava.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 *  数据响应处理器
 */
@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcResponse> rpcResponseMessageProtocol) throws Exception {
        String requestId = rpcResponseMessageProtocol.getHeader().getRequestId();
        // 收到响应 设置响应数据
        LocalRpcResponseCache.fillResponse(requestId, rpcResponseMessageProtocol);
    }
}
