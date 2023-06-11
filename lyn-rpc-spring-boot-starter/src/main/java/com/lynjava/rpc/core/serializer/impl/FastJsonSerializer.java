package com.lynjava.rpc.core.serializer.impl;



import com.alibaba.fastjson.JSON;
import com.lynjava.rpc.core.serializer.ISerializer;

import java.io.IOException;

public class FastJsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return (T)JSON.parseObject(new String(data), clz);
    }
}
