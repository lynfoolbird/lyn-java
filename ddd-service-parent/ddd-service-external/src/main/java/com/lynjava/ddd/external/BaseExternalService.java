package com.lynjava.ddd.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.RequestDataWrapper;
import com.lynjava.ddd.common.model.ResponseDataWrapper;
import com.lynjava.ddd.external.common.consts.RequestURIEnum;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

/**
 * 防腐层公共：可封装http请求等
 */
public abstract class BaseExternalService {

    @Inject
    private RestTemplate restTemplate;

    public String sendRequest(RequestURIEnum uriEnum, Object bodyJson, String...params) {
        // 请求头
        HttpHeaders headers = buildHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(bodyJson, headers);
        String url = uriEnum.buildRequestUrl(getGateway(), params);
        // 发送rest请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, uriEnum.getMethod(), httpEntity, String.class);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        return responseEntity.getBody();
    }

    public <T> T sendRequest(RequestURIEnum uriEnum, Object bodyJson, Class<T> clazz, String...params) {
        // 请求头
        HttpHeaders headers = buildHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(bodyJson, headers);
        String url = uriEnum.buildRequestUrl(getGateway(), params);
        // 发送rest请求
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, uriEnum.getMethod(), httpEntity, clazz);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        return responseEntity.getBody();
    }

    /**
     * 响应带泛型参数
     * @param url
     * @param httpMethod
     * @param object
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T sendRequest(String url, HttpMethod httpMethod, Object object, TypeReference<T> typeReference) {
        // 请求体
        RequestDataWrapper<Object> requestBody = RequestDataWrapper.<Object>builder()
                .type("CLUSTER")
                .data(RequestDataWrapper.Data.<Object>builder().attributes(object).build())
                .build();
        // 请求头
        HttpHeaders headers = buildHeaders();
        HttpEntity<RequestDataWrapper<Object>> httpEntity = new HttpEntity<>(requestBody, headers);
        // 发送rest请求
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, httpMethod, httpEntity, String.class);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        String responseBody = responseEntity.getBody();
        ResponseDataWrapper responseDataWrapper = JSON.parseObject(responseBody, ResponseDataWrapper.class);
        Object dataObj = responseDataWrapper.getData();
        if (dataObj instanceof JSONObject) {
            return ((JSONObject) dataObj).toJavaObject(typeReference);
        } else if (dataObj instanceof JSONArray) {
            return ((JSONArray) dataObj).toJavaObject(typeReference);
        } else {
            return (T) dataObj;
        }
    }

    /**
     * 响应不带泛型参数
     * @param url
     * @param httpMethod
     * @param object
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T sendRequest(String url, HttpMethod httpMethod, Object object, Class<T> clazz) {
        // 请求体
        RequestDataWrapper<Object> requestBody = RequestDataWrapper.<Object>builder()
                .type("CLUSTER")
                .data(RequestDataWrapper.Data.<Object>builder().attributes(object).build())
                .build();
        // 请求头
        HttpHeaders headers = buildHeaders();
        HttpEntity<RequestDataWrapper<Object>> httpEntity = new HttpEntity<>(requestBody, headers);
        // 发送rest请求
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, httpMethod, httpEntity, String.class);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        String responseBody = responseEntity.getBody();
        ResponseDataWrapper responseDataWrapper = JSON.parseObject(responseBody, ResponseDataWrapper.class);
        Object dataObj = responseDataWrapper.getData();
        if (dataObj instanceof JSONObject) {
            return ((JSONObject) dataObj).toJavaObject(clazz);
        } else if (dataObj instanceof JSONArray) {
            return ((JSONArray) dataObj).toJavaObject(clazz);
        } else {
            return (T) dataObj;
        }
    }

    protected abstract String getGateway();

    /**
     * 请求头
     * @return
     */
    protected HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("ContentType", "application/json");
        return headers;
    }
}
