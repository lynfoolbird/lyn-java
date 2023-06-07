package com.lynjava.rpc.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkSerializer implements ISerializer {


    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream=
                new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream=
                    new ObjectOutputStream(byteArrayOutputStream);

            outputStream.writeObject(obj);

            return  byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream=
                    new ObjectInputStream(byteArrayInputStream);

            return (T) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
