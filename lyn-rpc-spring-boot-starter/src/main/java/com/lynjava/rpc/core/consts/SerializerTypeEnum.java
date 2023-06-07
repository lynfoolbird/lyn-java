package com.lynjava.rpc.core.consts;

import lombok.Getter;

public enum SerializerTypeEnum {

    /**
     * HESSIAN
     */
    HESSIAN((byte) 0),
    JSON((byte) 1),
    FASTJSON((byte) 2);

    @Getter
    private byte type;

    SerializerTypeEnum(byte type) {
        this.type = type;
    }

    public static SerializerTypeEnum parseByName(String typeName) {
        for (SerializerTypeEnum typeEnum : SerializerTypeEnum.values()) {
            if (typeEnum.name().equalsIgnoreCase(typeName)) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

    public static SerializerTypeEnum parseByType(byte type) {
        for (SerializerTypeEnum typeEnum : SerializerTypeEnum.values()) {
            if (typeEnum.getType() == type) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

}
