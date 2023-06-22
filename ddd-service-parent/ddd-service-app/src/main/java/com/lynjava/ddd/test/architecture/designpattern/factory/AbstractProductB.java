package com.lynjava.ddd.test.architecture.designpattern.factory;

/**
 * B类产品：如手机
 */
public abstract class AbstractProductB {
    private String company;

    public AbstractProductB(String company) {
        this.company = company;
    }
}
