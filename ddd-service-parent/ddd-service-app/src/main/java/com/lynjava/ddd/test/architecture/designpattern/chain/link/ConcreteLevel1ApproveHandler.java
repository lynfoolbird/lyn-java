package com.lynjava.ddd.test.architecture.designpattern.chain.link;

import com.lynjava.ddd.test.architecture.designpattern.chain.ApplyContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.link.AbstractApproveHandler;

public class ConcreteLevel1ApproveHandler extends AbstractApproveHandler {
    @Override
    public void process(ApplyContext context) throws Exception {
        System.out.println("ConcreteLevel1ApproveHandler process");
        context.setSuccess(true);
    }
}
