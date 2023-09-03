package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.ApplyContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.InvokeResult;

public class EndPointPlugin implements IPlugin {

    @Override
    public InvokeResult invoke(ApplyContext context, Chain chain) throws Exception {
        System.out.println("do something");
        return chain.proceed();
    }
}

