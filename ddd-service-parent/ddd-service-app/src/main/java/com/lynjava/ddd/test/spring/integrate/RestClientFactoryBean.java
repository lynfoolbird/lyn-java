package com.lynjava.ddd.test.spring.integrate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class RestClientFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clazz;

    @Autowired
    private RestTemplate restTemplate;

    public RestClientFactoryBean(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                (o, method, args) -> {
                    RestCall restCall = method.getAnnotation(RestCall.class);
                    if (Objects.nonNull(restCall)) {
                        RestClient restClient = clazz.getAnnotation(RestClient.class);
                        String url = restClient.baseUrl() + restCall.path();
                        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(args[0]));
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
                        String bodyJson = responseEntity.getBody();
                        return JSONObject.parseObject(bodyJson, method.getReturnType());
                    }
                    return method.invoke(this, args);
                });
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
