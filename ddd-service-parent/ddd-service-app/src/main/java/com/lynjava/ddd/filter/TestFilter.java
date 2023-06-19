package com.lynjava.ddd.filter;

import cn.hutool.http.ContentType;
import com.lynjava.ddd.common.model.DddServletRequestWrapper;
import com.lynjava.ddd.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        // 包装request，可以打印多次body
        String body = "";
        // 如果是文件上传，不打印body体
        boolean notFileUpload = !StringUtils.startsWith(request.getContentType(), ContentType.MULTIPART.getValue());
        if (notFileUpload) {
            request = new DddServletRequestWrapper(request);
            body = ((DddServletRequestWrapper) request).getBody()
                    .replaceAll("[\r\n\t]", "").replaceAll("\"", "'");
        }
        Map<String, Object> params = CommonUtils.getRequestParams(request);
        Map<String, Object> headers = CommonUtils.getRequestHeaders(request);
        log.info("RequestURL: {}  {}", request.getMethod(), request.getRequestURL() + "?" +request.getQueryString());
        log.info("RequestHeader is {}", headers);
        log.info("RequestBody is {}", body);
        // 下面一行必须有，使用包装后的流，否则无法传递
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("log filter destroy .......");
    }
}
