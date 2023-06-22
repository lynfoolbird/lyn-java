package com.lynjava.ddd.test.architecture.designpattern.factory;

/**
 * A类产品：如PC
 */
public abstract class AbstractProductA {
    private String company;

    public AbstractProductA(String company) {
        this.company = company;
    }
}
