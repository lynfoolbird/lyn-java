package com.lynjava.rpc.core.protocol;

import com.lynjava.rpc.core.consts.MsgTypeEnum;
import com.lynjava.rpc.core.consts.RpcConstants;
import com.lynjava.rpc.core.consts.SerializerTypeEnum;
import com.lynjava.rpc.core.util.RpcUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * RPC协议消息头
 *
 * @author li
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageHeader implements Serializable {

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    | 状态 1byte |        消息 ID 32byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    */
    /**
     *  魔数
     */
    private short magic;

    /**
     *  协议版本号
     */
    private byte version;

    /**
     *  序列化算法
     */
    private byte serialization;

    /**
     *  报文类型
     */
    private byte msgType;

    /**
     *  状态
     */
    private byte status;

    /**
     *  消息 ID
     */
    private String requestId;

    /**
     *  数据长度
     */
    private int msgLen;


    public static MessageHeader build(String serialization){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMagic(RpcConstants.PROTOCOL.MAGIC);
        messageHeader.setVersion(RpcConstants.PROTOCOL.VERSION);
        messageHeader.setRequestId(RpcUtils.getUuid());
        messageHeader.setMsgType(MsgTypeEnum.REQUEST.getType());
        messageHeader.setSerialization(SerializerTypeEnum.parseByName(serialization).getType());
        return messageHeader;
    }
}
