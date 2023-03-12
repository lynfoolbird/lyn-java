package com.lynjava.ddd.test.architecture.designpattern.strategy;

import org.springframework.stereotype.Service;

@Service("StrategyB")
public class StrategyBoperateServiceImpl implements IOperateService {

    public String getOperateType() {
        return "StrategyB";
    }

    public void doSomething() {
        System.out.println("execute StrategyB operate");
    }
}
