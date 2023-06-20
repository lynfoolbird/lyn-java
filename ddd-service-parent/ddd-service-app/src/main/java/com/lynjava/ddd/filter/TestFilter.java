package com.lynjava.ddd.filter;

import com.lynjava.ddd.common.model.MultiReadServletRequestWrapper;
import com.lynjava.ddd.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet过滤器
 */
@Component
@Slf4j
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init .......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("RequestURL: {}  {}", request.getMethod(), request.getRequestURL() + "?" +request.getQueryString());
        log.info("RequestHeader is {}", CommonUtils.getRequestHeaders(request));
        Map<String, Object> params = CommonUtils.getRequestParams(request);
        String contentType = request.getContentType();
        // 表单请求
        if (StringUtils.startsWith(contentType, MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {


        } else if (StringUtils.startsWith(contentType, MediaType.MULTIPART_FORM_DATA_VALUE)) {
            // 如果是文件上传，不打印body体

        } else {
            // 包装request，可以打印多次body
            if (!HttpMethod.GET.matches(request.getMethod())) {
                request = new MultiReadServletRequestWrapper(request);
                log.info("RequestBody is {}", CommonUtils.getRequestBody(request));
            }
        }
        // 下面一行必须有，使用包装后的流，否则无法传递
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("log filter destroy .......");
    }
}
