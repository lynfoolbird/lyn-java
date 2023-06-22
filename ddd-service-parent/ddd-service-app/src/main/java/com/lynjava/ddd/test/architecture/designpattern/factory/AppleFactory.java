package com.lynjava.ddd.test.architecture.designpattern.factory;

/**
 * 苹果工厂：生产苹果手机，苹果PC，。。。
 */
public class AppleFactory extends AbstractFactory {
    @Override
    public AbstractProductA createProductA() {
        return new ProductA("APPLE");
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductB("APPLE");
    }
}
