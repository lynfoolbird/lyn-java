package com.lynjava.ddd.external.anticorruption;


import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.AbstractCallService;

import java.lang.reflect.Constructor;

public class CallServiceBuilder {
    private URIEnum uri;
    private Object param;

    public static CallServiceBuilder create(){
        return new CallServiceBuilder();
    }

    public CallServiceBuilder setUri(URIEnum uri) {
        this.uri = uri;
        return this;
    }

    public CallServiceBuilder setParam(Object param) {
        this.param = param;
        return this;
    }

    public <T extends AbstractCallService> AbstractCallService buildCallService(Class<T> clazz) {
        try {
            Constructor constroctor = clazz.getConstructor(URIEnum.class, Object.class);
            AbstractCallService service = (AbstractCallService) constroctor.newInstance(this.uri, this.param);
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T extends AbstractCallService> AbstractCallService buildCallService2(Class<T> clazz, URIEnum uri, Object param) {
        try {
            Constructor constroctor = clazz.getConstructor(URIEnum.class, Object.class);
            AbstractCallService service = (AbstractCallService) constroctor.newInstance(uri, param);
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCallService> AbstractCallService buildCallService3(Class<T> clazz) {
        try {
            AbstractCallService service = clazz.newInstance();
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
