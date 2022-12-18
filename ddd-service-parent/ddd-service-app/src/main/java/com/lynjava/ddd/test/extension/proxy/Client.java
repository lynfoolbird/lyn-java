package com.lynjava.ddd.test.extension.proxy;

import java.util.Arrays;
import java.util.List;

/**
 * JDK动态代理演示
 */
public class Client {
    public static void main(String[] args) {
        Session session = new Session();
        ITestPrint print = ProxyFactory.newInstance(ITestPrint.class, session);
        print.sayHello("pp");

        ITestPrint pIns = new ITestPrint() {
            public List<String> sayHello(String str) {
                System.out.println(str);
                return Arrays.asList(str);
            }
        };
        ITestPrint print2 = (ITestPrint) ProxyFactory.newInstance(pIns, session);
        print2.sayHello("abcdefg");
    }
}

class  Session {
    public List<String> select() {
        return Arrays.asList("one", "two", "three");
    }
}
