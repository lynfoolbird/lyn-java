package com.lynjava.ddd.domain.other;

import com.lynjava.ddd.common.utils.IRegistry;

public class EurekaRegistryImpl implements IRegistry {
    @Override
    public boolean registry(String hostname, Integer port) {
        System.out.println("registry to eureka, hostname is " + hostname + ",port is " + port);
        return false;
    }
}
