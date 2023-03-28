package com.lynjava.ddd.domain.shared;

import com.lynjava.ddd.common.utils.IRegistry;

public class ZookeeperRegistryImpl implements IRegistry {
    @Override
    public boolean registry(String hostname, Integer port) {
        System.out.println("registry to zookeeper, hostname is " + hostname + ",port is " + port);
        return false;
    }
}
