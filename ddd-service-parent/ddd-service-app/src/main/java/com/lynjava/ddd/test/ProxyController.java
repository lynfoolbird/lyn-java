package com.lynjava.ddd.test;

import com.lynjava.ddd.common.model.HttpResult;
import com.lynjava.ddd.common.utils.HttpClientUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求代理接口
 */
@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @GetMapping("/alb_ops_check")
    public void proxyGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String albScheme = request.getHeader("alb_scheme"); // 请求协议http、https
        String albHost = request.getHeader("alb_host"); // 目标主机
        String albProxyIp = request.getHeader("alb_proxy_ip"); // ip端口或域名
        String albProxyUri = request.getHeader("alb_proxy_uri"); // 目标uri
        StringBuilder url = new StringBuilder().append(albScheme)
                .append("://")
                .append(albProxyIp)
                .append(albProxyUri);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", albHost);
        // 其他Header透传过去，此处暂时省略
        HttpResult httpResult = HttpClientUtils.doGet(url.toString(), headers);
        response.setStatus(httpResult.getCode());
        if (httpResult.isSuccess()) {
            StreamUtils.copy(String.valueOf(httpResult.getData()).getBytes(), response.getOutputStream());
            return;
        }
        StreamUtils.copy(httpResult.getMessage().getBytes(), response.getOutputStream());
    }

    @PostMapping("/alb_ops_check")
    public void proxyPost(HttpServletRequest request, HttpServletResponse response, @RequestBody Object body) {
    }
}
