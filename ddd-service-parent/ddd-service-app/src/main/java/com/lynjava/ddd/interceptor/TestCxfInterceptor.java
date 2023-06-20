package com.lynjava.ddd.interceptor;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 自定义CXF拦截器
 * https://blog.csdn.net/weixin_33935505/article/details/92663563
 */
@Component
public class TestCxfInterceptor extends AbstractPhaseInterceptor<Message> {
    public TestCxfInterceptor(String phase) {
        super(phase);
    }

    public TestCxfInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        System.out.println("TestCxfInterceptor handleMessage:" + message);
        HttpServletRequest request = (HttpServletRequest)message.get("HTTP.REQUEST");
        HttpSession httpSession = request.getSession();
        boolean newSession = false;

        String userName = (String)httpSession.getAttribute("userName");
        String password = null;

        if (userName != null) {
            password = (String)httpSession.getAttribute("password");
        } else {
            AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
            if (policy != null) {
                userName = policy.getUserName();
                password = policy.getPassword();
                newSession = true;
            }
        }
        ClassResourceInfo classResourceInfo = JAXRSUtils.getRootResource(message);
    }
}
