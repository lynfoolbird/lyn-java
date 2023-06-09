package com.lynjava.ddd.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author li
 */
public final class CopyUtils {

    /**
     * 深拷贝-FastJson序列化
     *
     * @param object
     * @param clazz
     * @return T
     * @param <T>
     */
    public static <T> T deepCopy1(Object object, Class<T> clazz) {
        String json = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(json, clazz);
    }

}
