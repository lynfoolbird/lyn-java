package com.lynjava.rpc.client.transport;

import com.lynjava.rpc.core.model.RpcRequest;
import com.lynjava.rpc.core.protocol.MessageProtocol;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RequestMetadata
 * @Description 请求元数据
 */
@Data
@Builder
public class RequestMetadata implements Serializable {

    /**
     *  协议
     */
    private MessageProtocol<RpcRequest> protocol;

    /**
     *  地址
     */
    private String address;

    /**
     *  端口
     */
    private Integer port;

    /**
     *  服务调用超时
     */
    private Integer timeout;

}
