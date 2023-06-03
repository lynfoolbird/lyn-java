package com.lynjava.ddd.common.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author li
 */
public final class CommonUtils {

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
