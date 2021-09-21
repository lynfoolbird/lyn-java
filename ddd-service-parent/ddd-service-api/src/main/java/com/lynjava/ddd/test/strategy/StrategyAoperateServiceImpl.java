package com.lynjava.ddd.test.strategy;

import org.springframework.stereotype.Service;

@Service
public class StrategyAoperateServiceImpl implements IOperateService {
    public String getOperateType() {
        return "StrategyB";
    }

    public void doSomething() {
        System.out.println("execute StrategyB operate");
    }
}
