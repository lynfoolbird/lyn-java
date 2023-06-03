package com.lynjava.ddd.test.extension.spi;

import com.lynjava.ddd.common.other.IRegistry;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JdkSpiTest {

    public static void main(String[] args) {
        ServiceLoader<IRegistry> serviceLoader = ServiceLoader.load(IRegistry.class);
        Iterator<IRegistry> itr = serviceLoader.iterator();
        while (itr.hasNext()) {
            IRegistry registry = itr.next();
            registry.registry("127.0.0.1", 8081);
        }
    }
}
