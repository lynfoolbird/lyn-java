package com.lynjava.ddd.test.architecture.designpattern.strategy;

import org.springframework.stereotype.Service;

// 用bean的名称来区分，根据名称从容器获取bean
@Service("StrategyA")
public class StrategyAoperateServiceImpl implements IOperateService {
    public String getOperateType() {
        return "StrategyA";
    }

    public void doSomething() {
        System.out.println("execute StrategyA operate");
    }
}
