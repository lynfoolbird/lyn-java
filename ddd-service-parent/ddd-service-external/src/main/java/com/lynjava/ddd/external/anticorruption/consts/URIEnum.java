package com.lynjava.ddd.external.anticorruption.consts;


import com.lynjava.ddd.external.anticorruption.beans.IConvertBean;
import com.lynjava.ddd.external.anticorruption.beans.SyncStaticRouterConvertBean;

import java.util.Map;
import java.util.Objects;

public enum URIEnum {
    /**
     *
     */
    ALB_SYNC_STATIC_ROUTER("/alb_service/rules/{ruleId}/syncStaticRouter?type={type}", RestMethodTypeEnum.POST, SyncStaticRouterConvertBean.class);


    private String uri;
    private RestMethodTypeEnum method;
    private Class convertBeanCls;

    <T extends IConvertBean> URIEnum(String uri, RestMethodTypeEnum method, Class<T> clazz) {
        this.uri = uri;
        this.method = method;
        this.convertBeanCls = clazz;
    }

    public String getUri(String...params) {
        return generateRequestUrl(params);
    }

    public RestMethodTypeEnum getMethod() {
        return method;
    }

    public Class getConvertBeanCls() {
        return convertBeanCls;
    }

    /**
     * 根据参数动态构建url
     *
     * @param params
     * @return
     */
    public String generateRequestUrl(String...params) {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(params) || params.length == 0) {
            return sb.append(uri).toString();
        }
        // 1、获取所有参数名
        String[] firstStrArr = uri.split("\\{");
        String[] paramNames = new String[firstStrArr.length];
        for (int i=0; i<firstStrArr.length; i++) {
            if (!firstStrArr[i].contains("}")) {
                continue;
            }
            String[] secondStrArr = firstStrArr[i].split("}");
            paramNames[i - 1] = secondStrArr[0];
        }
        // 2、用值替换所有参数
        String newUri = uri;
        for (int i=0; i<params.length; i++) {
            newUri = newUri.replace("{" + paramNames[i] + "}", params[i]);
        }
        return sb.append(newUri).toString();
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
