package com.lynjava.rpc.core.serializer;

import com.lynjava.rpc.core.consts.SerializerTypeEnum;
import com.lynjava.rpc.core.serializer.impl.FastJsonSerializer;
import com.lynjava.rpc.core.serializer.impl.HessianSerializer;
import com.lynjava.rpc.core.serializer.impl.JacksonSerializer;
import com.lynjava.rpc.core.serializer.impl.JdkSerializer;
import com.lynjava.rpc.core.serializer.impl.ProtobufSerializer;

/**
 * 序列化器工厂
 *
 * @author li
 */
public class SerializerFactory {

    public static ISerializer getSerializer(SerializerTypeEnum typeEnum) {
        switch (typeEnum) {
            case HESSIAN:
                return new HessianSerializer();
            case JACKSON:
                return new JacksonSerializer();
            case FASTJSON:
                return new FastJsonSerializer();
            case PROTOBUF:
                return new ProtobufSerializer();
            default:
                return new JdkSerializer();
        }
    }

}
