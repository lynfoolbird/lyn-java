package com.lynjava.ddd.external.common.consts;

import com.lynjava.ddd.common.consts.CommonConstants;
import org.springframework.http.HttpMethod;

import java.util.regex.Matcher;

public enum RequestURIEnum {
    /**
     *
     */
    SERVICE_MARKET_CREATE_ORDER("/service-market/order/{type}", HttpMethod.POST),
    /**
     *
     */
    ALB_GET_ROUTER("/alb_service/rules/{ruleId}?type={type}", HttpMethod.GET);


    private String uri;
    private HttpMethod method;

    RequestURIEnum(String uri, HttpMethod method) {
        this.uri = uri;
        this.method = method;
    }

    public String buildRequestUrl(String gateway, String...params) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(gateway);
        Matcher mather = CommonConstants.RegexPattern.EXTRACT_PARAMS.matcher(this.uri);
        String finalUrl = this.uri;
        int index = 0;
        while (mather.find() && index<params.length) {
            finalUrl = finalUrl.replace(mather.group(), params[index]);
            index++;
        }
        return sb.append(finalUrl).toString();
    }

    public HttpMethod getMethod() {
        return method;
    }
}
