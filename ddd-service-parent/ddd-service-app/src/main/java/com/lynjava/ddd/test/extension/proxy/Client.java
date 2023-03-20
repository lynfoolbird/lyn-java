package com.lynjava.ddd.test.extension.proxy;

import java.util.Arrays;
import java.util.List;

/**
 * JDK动态代理演示
 */
public class Client {
    public static void main(String[] args) {
        Session session = new Session();
        ITestDao print = ProxyFactory.newInstance(ITestDao.class, session);
        print.select("001select");
        print.insert("001insert");

        System.out.println("=======================================");

        ITestDao pIns = new ITestDao() {
            public List<String> select(String str) {
                System.out.println(str);
                return Arrays.asList(str);
            }

            @Override
            public int insert(String param) {
                System.out.println(param);
                return 0;
            }
        };
        ITestDao print2 = (ITestDao) ProxyFactory.newInstance(pIns, session);
        print2.select("002select");
        print2.insert("002insert");
    }
}

class  Session {
    public List<String> select(String param) {
        System.out.println("execute select sql..." + param);
        return Arrays.asList("one", "two", "three");
    }

    public int insert(String param) {
        System.out.println("execute insert sql..." + param);
        return 1;
    }
}
