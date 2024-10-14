package com.lynjava.ddd.test.architecture.designpattern.chain.link;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;

public class ConcreateLevel2ApproveHandler extends AbstractApproveHandler {
    @Override
    public void process(GatewayContext context) throws Exception{
        System.out.println("ConcreateLevel2ApproveHandler process");
        context.setSuccess(true);
    }
}
