package com.lynjava.ddd.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    /**
     * x-www-form-urlencoded提交时
     * 若是GET请求则将参数拼接到url中，
     * POST请求时会放到body体中，以编码后字符串形式存在，下面方法可将其转换为Map形式
     * 注意：应先使用&、=分割后再解码；若先解码再分割，则如果原字符串中存在&、=将导致解析错误
     * @param urlParam
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> transUrlParams2Map(String urlParam, String charset) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new HashMap<>();
        if (StringUtils.isBlank(urlParam)) {
            return resultMap;
        }
        String[] paramArr =  urlParam.split("&");
        for (String param : paramArr) {
            String[] keyValue = param.split("=");
            if (keyValue.length < 2) {
                continue;
            }
            String key = URLDecoder.decode(keyValue[0], charset);
            String value = URLDecoder.decode(keyValue[1], charset);
            resultMap.put(key, value);
        }
        return resultMap;
    }
}
