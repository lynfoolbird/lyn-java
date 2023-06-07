package com.lynjava.rpc.core.serializer;

import java.io.IOException;

public interface ISerializer {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}