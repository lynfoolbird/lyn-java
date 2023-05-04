package com.lynjava.ddd.common.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web请求上下文
 * 完整应包含用户登录信息（ThreadLocal保存）
 *
 * @author li
 */
public final class DddRequestContext {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        return response;
    }

    public static Object getAttribute(String key) {
        HttpServletRequest request = getRequest();
        return request.getAttribute(key);
    }

    /**
     * 在请求上下文中添加属性，如操作批次号
     * @param key
     * @param value
     */
    public static void addAttribute(String key, Object value) {
        HttpServletRequest request = getRequest();
        request.setAttribute(key, value);
    }
}
