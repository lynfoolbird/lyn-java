package com.lynjava.ddd.test.anticorruption.consts;


import com.lynjava.ddd.test.anticorruption.beans.IConvertBean;
import com.lynjava.ddd.test.anticorruption.beans.SyncStaticRouterConvertBean;

public enum URIEnum {
    ALB_SYNC_STATIC_ROUTER("/alb_service/rules/{ruleId}/syncStaticRouter", RestMethodTypeEnum.POST, SyncStaticRouterConvertBean.class);


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

    @Override
    public String toString() {
        return this.uri;
    }
}
