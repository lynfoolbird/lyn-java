package com.lynjava.rpc.core.serializer;

import com.lynjava.rpc.core.consts.SerializerTypeEnum;

/**
 * 序列化器工厂
 *
 * @author li
 */
public class SerializerFactory {

    public static ISerializer getRpcSerialization(SerializerTypeEnum typeEnum) {
        switch (typeEnum) {
            case HESSIAN:
//                return new HessianSerializer();
            case JSON:
//                return new JacksonSerializer();
            case FASTJSON:
//                return new FastJsonSerializer();
            default:
                return new JdkSerializer();
        }
    }

}
