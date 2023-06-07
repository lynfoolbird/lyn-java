package com.lynjava.rpc.core.consts;

import lombok.Getter;

public enum MsgTypeEnum {
    /**
     * 请求消息
     */
    REQUEST((byte) 1),
    /**
     * 响应消息
     */
    RESPONSE((byte) 2);

    @Getter
    private byte type;

    MsgTypeEnum(byte type) {
        this.type = type;
    }

    public static MsgTypeEnum findByType(byte type) {
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            if (msgTypeEnum.getType() == type) {
                return msgTypeEnum;
            }
        }
        return null;
    }
}
