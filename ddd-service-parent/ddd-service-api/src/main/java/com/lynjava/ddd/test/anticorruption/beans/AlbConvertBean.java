package com.lynjava.ddd.test.anticorruption.beans;

import java.util.Map;

public class AlbConvertBean extends DefaultConvertBean {

    @Override
    public Map<String, Object> convertHeader(Object param) {
        Map<String, Object> headers = super.convertHeader(param);
        headers.put("Auth", "alb");
        return headers;
    }
}
