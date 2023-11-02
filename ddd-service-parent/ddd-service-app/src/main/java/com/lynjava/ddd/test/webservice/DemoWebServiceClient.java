package com.lynjava.ddd.test.webservice;

import com.lynjava.ddd.test.webservice.impl.WeatherDemoWebServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

public class DemoWebServiceClient {
    public static void main(String[] args) {
        // 1、使用Endpoint发布webservice，放到启动类main方法即可
        Endpoint.publish("http://127.0.0.1:12345/weather",new WeatherDemoWebServiceImpl());

        // 2、查看wsdl，浏览器访问http://127.0.0.1:12345/weather?wsdl

        // 3、调用webservice服务，可使用restTemplate调用
        String url = "http://127.0.0.1:12345/weather";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml;charset=utf8");
        Object body = getXML("xian");
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String res = responseEntity.getBody();
        System.out.println(res);
    }

    public static String  getXML(String cityName) {
        return "<?xml version=\"1.0\" ?>"
                + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<S:Body>"
                + "<ns2:queryWeather xmlns:ns2=\"http://service.cn_lc\">"
                + "<cityName>"+cityName+"</cityName>"
                + "</ns2:queryWeather>"
                + "</S:Body>"
                + "</S:Envelope>";
    }
}
