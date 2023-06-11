package com.lynjava.rpc.core.serializer.impl;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.lynjava.rpc.core.serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements ISerializer {

    /**
     *  序列化
     * @param object
     * @param <T>
     * @return
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }
        byte[] results;

        HessianSerializerOutput hessianOutput;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            hessianOutput = new HessianSerializerOutput(os);
            hessianOutput.writeObject(object);
            hessianOutput.flush();
            results = os.toByteArray();
        } catch (IOException e) {
            throw e;
        }

        return results;
    }

    /**
     *  反序列化
     * @param bytes
     * @param clz
     * @param <T>
     * @return
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        if (bytes == null) {
            throw new NullPointerException();
        }
        T result;

        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            HessianSerializerInput hessianInput = new HessianSerializerInput(is);
            result = (T) hessianInput.readObject(clz);
        } catch (IOException e) {
            throw e;
        }

        return result;
    }
}
