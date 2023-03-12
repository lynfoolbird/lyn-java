package com.lynjava.ddd.test.architecture.anticorruption.beans;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class SyncStaticRouterConvertBean extends AlbConvertBean {
    @Override
    public Map<String, Object> convertHeader(Object param) {
        Map<String, Object> headers = super.convertHeader(param);
        headers.put("operateType", "syncStaticRouter");
        return headers;
    }

    @Override
    public Map<String, Object> convertPathParam(Object param) {
        Map<String, Object> pathParams = new HashMap<String, Object>();
        pathParams.put("ruleId", "123456");
        return pathParams;
    }

    @Override
    public Map<String, Object> convertQueryParam(Object param) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("appId", "414");
        return queryParams;
    }

    @Override
    public String convertBody(Object param) {
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("compEnvId", param);
        return JSON.toJSONString(bodyMap);
    }
}
