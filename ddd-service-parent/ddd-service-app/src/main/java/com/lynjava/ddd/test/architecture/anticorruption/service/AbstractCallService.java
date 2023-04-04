package com.lynjava.ddd.test.architecture.anticorruption.service;


import com.lynjava.ddd.test.architecture.anticorruption.beans.IConvertBean;
import com.lynjava.ddd.test.architecture.anticorruption.consts.URIEnum;

import java.util.Map;

public class AbstractCallService implements ICallService {

    private URIEnum uri;
    private Object param;

    public AbstractCallService(){
    }

    public AbstractCallService(URIEnum uri, Object param) {
        this.uri = uri;
        this.param = param;
    }

    public String getGateway() {
        return null;
    }

    public String execute() throws Exception {
        String url = this.getGateway() + this.uri.getUri();
        IConvertBean convertBean = (IConvertBean) this.uri.getConvertBeanCls().newInstance();
        String requestUrl = generateRequstUrl(url, convertBean.convertPathParam(param), convertBean.convertQueryParam(param));
        Map<String, Object> headers = convertBean.convertHeader(this.param);
        String bodyJson = convertBean.convertBody(this.param);
        return invoke(requestUrl, headers, bodyJson);
    }
    private String generateRequstUrl(String url, Map<String, Object> pathParam, Map<String, Object> queryParam){
        StringBuilder requstUrl = new StringBuilder();
        String resUrl = "";
        for (Map.Entry entry: pathParam.entrySet()) {
             resUrl = url.replace("{" + entry.getKey() + "}",(String)entry.getValue());
        }
        requstUrl.append(resUrl);
        if (queryParam.size()<1) {
          return requstUrl.toString();
        }
        requstUrl.append("?");
        for (Map.Entry entry:queryParam.entrySet()) {
            requstUrl.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        requstUrl.deleteCharAt(requstUrl.length() - 1);
        return requstUrl.toString();
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
