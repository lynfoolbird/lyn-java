package com.lynjava.ddd.test.architecture.designpattern.factory;

/**
 * 小米工厂：生产小米手机，小米PC，。。。
 */
public class MiFactory extends AbstractFactory {
    @Override
    public AbstractProductA createProductA() {
        return new ProductA("MI");
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductB("MI");
    }
}
