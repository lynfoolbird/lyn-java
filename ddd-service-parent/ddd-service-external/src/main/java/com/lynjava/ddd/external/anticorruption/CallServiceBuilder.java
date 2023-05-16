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
            Constructor<T> constructor = clazz.getConstructor(URIEnum.class, Object.class);
            return constructor.newInstance(this.uri, this.param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
