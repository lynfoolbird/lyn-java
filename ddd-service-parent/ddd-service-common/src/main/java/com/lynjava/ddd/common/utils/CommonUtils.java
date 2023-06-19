package com.lynjava.ddd.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author li
 */
public final class CommonUtils {

    public static String getId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").toUpperCase();
    }



    /**
     * 获取请求地址上的参数
     */
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Enumeration<String> enu = request.getParameterNames();
        Map<String, Object> params = new HashMap<>();
        //获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    public static Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Enumeration<String> enu = request.getHeaderNames();
        Map<String, Object> params = new HashMap<>();
        //获取请求头
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            params.put(name, request.getHeader(name));
        }
        return params;
    }
}
