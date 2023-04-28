package com.lynjava.ddd.external.anticorruption.beans;

import java.util.HashMap;
import java.util.Map;

public class DefaultConvertBean implements IConvertBean {
    public String convertBody(Object param) {
        return null;
    }

    public Map<String, Object> convertHeader(Object param) {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public Map<String, Object> convertPathParam(Object param) {
        return null;
    }

    public Map<String, Object> convertQueryParam(Object param) {
        return null;
    }
}
