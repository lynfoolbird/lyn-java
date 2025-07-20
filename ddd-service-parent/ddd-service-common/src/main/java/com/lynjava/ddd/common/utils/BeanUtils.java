package com.lynjava.ddd.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.util.IOUtils;
import org.springframework.util.ReflectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author li
 */
public final class BeanUtils {

    /**
     * 深拷贝-FastJson序列化
     *
     * @param object
     * @param clazz
     * @return T
     * @param <T>
     */
    public static <T> T deepCopy(Object object, Class<T> clazz) {
        String json = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(json, clazz);
    }

    public <T extends Serializable> List<T> deepCopyList(List<T> resList) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos);
        ) {
            oos.writeObject(resList);
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        } finally {
            IOUtils.close(bis);
            IOUtils.close(ois);
        }
    }

    /**
     * 是否IO流泄露
     * @param resList
     * @return
     * @param <T>
     */
    public <T extends Serializable> List<T> deepCopyListError(List<T> resList) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(resList);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 同类对象复制部分属性
     * @param srcObj
     * @param destObj
     * @param clazz
     * @param fieldNames
     * @param <T>
     */
    public static <T> void copyFields(T srcObj, T destObj, Class<T> clazz, String...fieldNames) {
        for (String fieldName : fieldNames) {
            Field field = ReflectionUtils.findField(clazz, fieldName);
            if (Objects.isNull(field)) {
                continue;
            }
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, srcObj);
            if (Objects.nonNull(fieldValue)) {
                ReflectionUtils.setField(field, destObj, fieldValue);
            }
        }
    }
    /**
     * 设置指定属性为null
     * @param object
     * @param clz
     * @param fieldNames
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void clearFields(Object object, Class<T> clz, String...fieldNames){
        if (Objects.isNull(object) || Objects.isNull(fieldNames)) {
            return;
        }
        Class clazz = object instanceof List ? clz : object.getClass();
        if (object instanceof List) {
            List list = (List) object;
            list.forEach(element -> clearObjectFields(element, clazz, fieldNames));
            return;
        }
        clearObjectFields(object, clazz, fieldNames);
    }

    /**
     * 设置指定属性为null
     * @param object
     * @param fieldNames
     */
    public static void clearFields(Object object, String...fieldNames){
        if (Objects.isNull(object) || Objects.isNull(fieldNames)) {
            return;
        }
        clearObjectFields(object, object.getClass(), fieldNames);
    }

    private static void clearObjectFields(Object object, Class clazz, String...fieldNames) {
        for (String fieldName : fieldNames) {
            Field field = ReflectionUtils.findField(clazz, fieldName);
            if (Objects.isNull(field)) {
                continue;
            }
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, object, null);
        }
    }
}
