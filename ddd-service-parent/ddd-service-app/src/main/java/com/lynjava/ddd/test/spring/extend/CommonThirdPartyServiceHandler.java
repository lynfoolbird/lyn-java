package com.lynjava.ddd.test.spring.extend;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author li
 */
@Slf4j
public class CommonThirdPartyServiceHandler implements ThirdPartyServiceHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在此可以编写给第三方发送 HTTP 请求的业务逻辑
        log.info("===> 发送请求到第三方了：{}", proxy.getClass().getName());
        return null;
    }
}
