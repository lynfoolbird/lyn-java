package com.lynjava.rpc.client.transport;

import com.lynjava.rpc.core.model.RpcResponse;
import com.lynjava.rpc.core.protocol.MessageProtocol;

/**
 *  NetClientTransport
 *  网络传输层
 */
public interface NetClientTransport {

    /**
     *  发送数据
     * @param metadata
     * @return
     * @throws Exception
     */
    MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception;

}
