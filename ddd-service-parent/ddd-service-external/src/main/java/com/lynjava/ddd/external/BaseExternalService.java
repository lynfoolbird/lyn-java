package com.lynjava.ddd.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.RequestDataWrapper;
import com.lynjava.ddd.common.model.ResponseDataWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

/**
 * 防腐层公共：可封装http请求等
 */
public class BaseExternalService {

    @Inject
    protected RestTemplate restTemplate;

    public <T> T sendRequest(String url, HttpMethod httpMethod, Object object, TypeReference<T> typeReference) {
        // 请求体
        RequestDataWrapper<Object> requestBody = RequestDataWrapper.<Object>builder()
                .type("CLUSTER")
                .data(RequestDataWrapper.Data.<Object>builder().attributes(object).build())
                .build();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("ContentType", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(requestBody), headers);
        // 发送rest请求
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, httpMethod, httpEntity, String.class);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        String responseBody = responseEntity.getBody();
        ResponseDataWrapper<T> response = JSON.parseObject(responseBody).toJavaObject(typeReference);
        return response.getData();
    }
}
