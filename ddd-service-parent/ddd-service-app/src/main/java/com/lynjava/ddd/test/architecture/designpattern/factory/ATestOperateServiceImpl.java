package com.lynjava.ddd.test.architecture.designpattern.factory;

public class ATestOperateServiceImpl implements ITestOperateService {
    @Override
    public void operate() {
        System.out.println(" operate a");
    }

    @Override
    public String opearteType() {
        return "OPRA";
    }
}
