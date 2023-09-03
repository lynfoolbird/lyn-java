package com.lynjava.ddd.test.architecture.designpattern.chain;

import com.lynjava.ddd.test.architecture.designpattern.chain.link.AbstractApproveHandler;
import com.lynjava.ddd.test.architecture.designpattern.chain.link.ConcreateLevel2ApproveHandler;
import com.lynjava.ddd.test.architecture.designpattern.chain.link.ConcreteLevel1ApproveHandler;

public class Client {
    public static void main(String[] args) throws Exception {
        AbstractApproveHandler handler = new AbstractApproveHandler.Builder()
                .addHandler(new ConcreteLevel1ApproveHandler())
                .addHandler(new ConcreateLevel2ApproveHandler())
                .build();
        handler.handler(new ApplyContext());
    }
}
