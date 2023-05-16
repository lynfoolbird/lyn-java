package com.lynjava.ddd.external.anticorruption;


import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.AbstractCallService;

import java.lang.reflect.Constructor;


public class CallServiceFactory {

    public static <T extends AbstractCallService> AbstractCallService createCallService(Class<T> clazz, URIEnum uri, Object param) {
        try {
            Constructor constroctor = clazz.getConstructor(URIEnum.class, Object.class);
            AbstractCallService service = (AbstractCallService) constroctor.newInstance(uri, param);
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCallService> AbstractCallService createCallService(Class<T> clazz) {
        try {
            AbstractCallService service = clazz.newInstance();
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
