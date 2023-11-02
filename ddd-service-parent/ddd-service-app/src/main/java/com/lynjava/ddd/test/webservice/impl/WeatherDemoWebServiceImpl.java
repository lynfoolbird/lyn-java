package com.lynjava.ddd.test.webservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.lynjava.ddd.test.webservice.IWeatherDemoWebService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(
        targetNamespace="http://service.cn_lc",
        name="WeatherWSSoap",
        portName="WeatherWSSoapPort",
        serviceName="WeatherWS"
)
public class WeatherDemoWebServiceImpl implements IWeatherDemoWebService {

    @WebMethod(
            operationName="queryWeather",
            exclude=false
    )
    @Override
    public  @WebResult(name="resultWeather")String queryWeather(@WebParam(name="cityName")String cityName) {
        JSONObject result = new JSONObject();
        result.put("cityName", cityName);
        result.put("msg", "hello world");
        return result.toJSONString();
    }
}
