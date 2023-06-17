package com.lynjava.ddd.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Servlet过滤器
 */
@Component
@Slf4j
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TestFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("Request: {}  {}", request.getMethod(), request.getRequestURL() + "?" +request.getQueryString());
        // 此处输入流只能获取一次，否则后续处理无法获取body
//        log.info("Body is {}", IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.toString()));
        System.out.println("requestHeader is " + request.getHeaderNames());
        // 下面一行必须有
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("TestFilter destroy");
    }
}
