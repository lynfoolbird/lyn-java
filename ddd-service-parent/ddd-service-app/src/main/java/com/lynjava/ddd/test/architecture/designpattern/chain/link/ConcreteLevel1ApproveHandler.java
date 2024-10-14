package com.lynjava.ddd.test.architecture.designpattern.chain.link;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;

public class ConcreteLevel1ApproveHandler extends AbstractApproveHandler {
    @Override
    public void process(GatewayContext context) throws Exception {
        System.out.println("ConcreteLevel1ApproveHandler process");
        context.setSuccess(true);
    }
}
