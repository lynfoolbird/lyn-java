package com.lynjava.rpc.core.serializer.impl;

import com.lynjava.rpc.core.serializer.ISerializer;

import java.io.IOException;

public class ProtobufSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return null;
    }
}
