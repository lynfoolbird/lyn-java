package com.lynjava.ddd.test.architecture.anticorruption.consts;


import com.lynjava.ddd.test.architecture.anticorruption.beans.IConvertBean;
import com.lynjava.ddd.test.architecture.anticorruption.beans.SyncStaticRouterConvertBean;

import java.util.Map;

public enum URIEnum {
    /**
     *
     */
    ALB_SYNC_STATIC_ROUTER("/alb_service/rules/{ruleId}/syncStaticRouter?type={type}", RestMethodTypeEnum.POST, SyncStaticRouterConvertBean.class);


    private String uri;
    private RestMethodTypeEnum method;
    private Class convertBeanCls;

    <T extends IConvertBean>URIEnum(String uri, RestMethodTypeEnum method, Class<T> clazz) {
        this.uri = uri;
        this.method = method;
        this.convertBeanCls = clazz;
    }

    public String getUri() {
        return uri;
    }

    public RestMethodTypeEnum getMethod() {
        return method;
    }

    public Class getConvertBeanCls() {
        return convertBeanCls;
    }

    // TODO 根据参数动态构建url
    public String buildUrl(String gateway, Object...params) {
        return this.uri;
    }

    public String generateRequestUrl(Map<String, Object> pathParam, Map<String, Object> queryParam){
        StringBuilder requestUrl = new StringBuilder();
        String resUrl = "";
        for (Map.Entry entry: pathParam.entrySet()) {
            resUrl = this.uri.replace("{" + entry.getKey() + "}", (String)entry.getValue());
        }
        requestUrl.append(resUrl);
        if (queryParam.size()<1) {
            return requestUrl.toString();
        }
        requestUrl.append("?");
        for (Map.Entry entry:queryParam.entrySet()) {
            requestUrl.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        requestUrl.deleteCharAt(requestUrl.length() - 1);
        return requestUrl.toString();
    }

    @Override
    public String toString() {
        return this.uri;
    }
}
