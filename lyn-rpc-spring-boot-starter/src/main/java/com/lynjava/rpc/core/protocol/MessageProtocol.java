package com.lynjava.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC协议消息
 *
 * @author li
 */
@Data
public class MessageProtocol<T> implements Serializable {

    /**
     *  消息头
     */
    private MessageHeader header;

    /**
     *  消息体
     */
    private T body;

}
