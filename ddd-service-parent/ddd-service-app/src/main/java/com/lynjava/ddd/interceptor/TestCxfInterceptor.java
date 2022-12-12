package com.lynjava.ddd.interceptor;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

/**
 * 自定义CXF拦截器
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
    }
}
