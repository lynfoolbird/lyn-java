package com.lynjava.ddd.external.anticorruption;


import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.AbstractCallService;

import java.lang.reflect.Constructor;


/**
 * @author li
 */
public class CallServiceFactory {

    public static <T extends AbstractCallService> AbstractCallService createCallService(Class<T> clazz, URIEnum uri, Object param) {
        try {
            Constructor<T> constructor = clazz.getConstructor(URIEnum.class, Object.class);
            return constructor.newInstance(uri, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCallService> AbstractCallService createCallService(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
