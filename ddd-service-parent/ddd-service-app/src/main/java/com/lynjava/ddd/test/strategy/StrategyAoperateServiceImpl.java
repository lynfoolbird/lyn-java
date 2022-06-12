package com.lynjava.ddd.test.strategy;

import org.springframework.stereotype.Service;

// 用bean的名称来区分，根据名称从容器获取bean
@Service("OPR-StrategyA")
public class StrategyAoperateServiceImpl implements IOperateService {
    public String getOperateType() {
        return "StrategyB";
    }

    public void doSomething() {
        System.out.println("execute StrategyB operate");
    }
}
