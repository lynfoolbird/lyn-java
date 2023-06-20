package com.lynjava.ddd.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author li
 */
@Slf4j
public final class CommonUtils {

    public static String getId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").toUpperCase();
    }

    public static String getRequestBody(HttpServletRequest request) {
        try (BufferedInputStream bis = new BufferedInputStream(request.getInputStream())) {
            String conLength = request.getHeader(HttpHeaders.CONTENT_LENGTH);
            int size = Objects.nonNull(conLength) ? Integer.parseInt(conLength) : 8192;
            byte[] bytes = new byte[size];
            IOUtils.read(bis, bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("getRequestBody exception:", e);
        }
        return null;
    }

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
