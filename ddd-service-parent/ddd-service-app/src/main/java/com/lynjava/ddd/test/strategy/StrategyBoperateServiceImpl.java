package com.lynjava.ddd.test.strategy;

import org.springframework.stereotype.Service;

@Service("OPR-StrategyB")
public class StrategyBoperateServiceImpl implements IOperateService {

    public String getOperateType() {
        return "StrategyA";
    }

    public void doSomething() {
        System.out.println("execute StrategyA operate");
    }
}
