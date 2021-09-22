package com.lynjava.ddd.test.anticorruption.beans;

import java.util.Map;

public interface IConvertBean {
    // 构造请求body
    String convertBody(Object param);

    // 构造请求头
    Map<String, Object> convertHeader(Object param);

    // 获取url路径参数
    Map<String, Object> convertPathParam(Object param);

    // 获取url查询参数
    Map<String, Object> convertQueryParam(Object param);
}
