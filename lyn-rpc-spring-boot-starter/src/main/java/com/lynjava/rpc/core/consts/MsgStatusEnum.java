package com.lynjava.rpc.core.consts;

import lombok.Getter;

public enum MsgStatusEnum {
    SUCCESS((byte)0),
    FAIL((byte)1);

    @Getter
    private final byte code;

    MsgStatusEnum(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code){
        return MsgStatusEnum.SUCCESS.code == code;
    }

}
