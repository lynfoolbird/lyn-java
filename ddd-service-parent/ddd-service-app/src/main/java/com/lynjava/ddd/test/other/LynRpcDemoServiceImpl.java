package com.lynjava.ddd.test.other;

import com.lynjava.ddd.common.other.ILynRpcDemoService;
import com.lynjava.rpc.core.annotation.LynRpcService;

@LynRpcService(interfaceType = ILynRpcDemoService.class)
public class LynRpcDemoServiceImpl implements ILynRpcDemoService {
    @Override
    public String doSomething(String hostname, Integer port) {
        System.out.println("doSomething: " + hostname + ":" + port);
        return hostname + ":" + port;
    }
}
