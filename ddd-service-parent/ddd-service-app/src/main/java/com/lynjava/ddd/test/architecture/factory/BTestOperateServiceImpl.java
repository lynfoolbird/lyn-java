package com.lynjava.ddd.test.architecture.factory;

public class BTestOperateServiceImpl implements ITestOperateService {
    @Override
    public void operate() {
        System.out.println(" operate b");
    }

    @Override
    public String opearteType() {
        return "OPRB";
    }
}