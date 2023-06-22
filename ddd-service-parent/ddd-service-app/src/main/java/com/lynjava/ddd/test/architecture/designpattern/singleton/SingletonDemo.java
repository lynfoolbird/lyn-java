package com.lynjava.ddd.test.architecture.designpattern.singleton;

public class SingletonDemo {
    // 构造器私有
    private SingletonDemo() {}

    // 饿汉式
    private static final SingletonDemo INSTANCE1 = new SingletonDemo();
    public static SingletonDemo getInstance1() {
        return INSTANCE1;
    }

    // 懒汉式
    private static SingletonDemo INSTANCE2;
    public synchronized static SingletonDemo getInstance2() {
        if (INSTANCE2 != null) {
            return INSTANCE2;
        }
        return new SingletonDemo();
    }

    // 双检锁式
    private static volatile SingletonDemo INSTANCE3;
    public static SingletonDemo getInstance3() {
        if (INSTANCE3 == null) {
            synchronized (SingletonDemo.class) {
                if (INSTANCE3 == null) {
                    INSTANCE3 = new SingletonDemo();
                }
            }
        }
        return INSTANCE3;
    }

    // 静态内部类
    private static class SingletonHolder {
        private static final SingletonDemo INSTANCE = new SingletonDemo();
    }
    public static final SingletonDemo getInstance4() {
        return SingletonHolder.INSTANCE;
    }

}

// 枚举式
enum SingletonEnum {
    INSTANCE;
}