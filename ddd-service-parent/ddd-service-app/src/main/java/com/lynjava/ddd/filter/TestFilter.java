package com.lynjava.ddd.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Servlet过滤器
 */
@Component
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TestFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("requestUrl is "+ request.getRequestURL());
        System.out.println("requestMethod is " + request.getMethod());
        System.out.println("requestHeader is " + request.getHeaderNames());
        // 下面一行必须有
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("TestFilter destroy");
    }
}
