package com.lynjava.ddd.domain.shared;

import com.lynjava.ddd.common.utils.IRegistry;

public class EurekaRegistryImpl implements IRegistry {
    @Override
    public boolean registry(String hostname, Integer port) {
        System.out.println("registry to eureka, hostname is " + hostname + ",port is " + port);
        return false;
    }
}
