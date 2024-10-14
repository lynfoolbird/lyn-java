package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.InvokeResult;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.IPlugin;

/**
 * 远程调用处理插件
 */
public class EndPointPlugin implements IPlugin {

    @Override
    public InvokeResult invoke(GatewayContext context, Chain chain) throws Exception {
        System.out.println("do something");
        return new InvokeResult();
    }
}

