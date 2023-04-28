package com.lynjava.ddd.external.anticorruption.service;


import com.lynjava.ddd.external.anticorruption.beans.IConvertBean;
import com.lynjava.ddd.external.anticorruption.consts.URIEnum;

import java.util.Map;

public abstract class AbstractCallService implements ICallService {

    private URIEnum uri;
    private Object param;

    public AbstractCallService(){
    }

    public AbstractCallService(URIEnum uri, Object param) {
        this.uri = uri;
        this.param = param;
    }

    public abstract String getGateway();

    public String execute() throws Exception {
        IConvertBean convertBean = (IConvertBean) this.uri.getConvertBeanCls().newInstance();
        String requestUrl = this.getGateway() + this.uri.generateRequestUrl(convertBean.convertPathParam(param), convertBean.convertQueryParam(param));
        Map<String, Object> headers = convertBean.convertHeader(this.param);
        String bodyJson = convertBean.convertBody(this.param);
        return invoke(requestUrl, headers, bodyJson);
    }

    private String invoke(String requestUrl, Map<String, Object> headers, String body) {
        switch (this.uri.getMethod()) {
            case POST:
                // 发送POST请求，获取响应
//                return HttpClientUtil.doPost(requestUrl, headers, body);
            case GET:
                // 发送GET请求，获取响应
//                return HttpClientUtil.doGet(requestUrl);
        }
        return null;
    }

    public URIEnum getUri() {
        return uri;
    }

    public AbstractCallService setUri(URIEnum uri) {
        this.uri = uri;
        return this;
    }

    public Object getParam() {
        return param;
    }

    public AbstractCallService setParam(Object param) {
        this.param = param;
        return this;
    }
}
