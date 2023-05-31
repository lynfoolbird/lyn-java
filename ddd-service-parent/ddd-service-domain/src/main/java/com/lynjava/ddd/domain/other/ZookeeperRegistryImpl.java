package com.lynjava.ddd.domain.other;

import com.lynjava.ddd.common.utils.IRegistry;

public class ZookeeperRegistryImpl implements IRegistry {
    @Override
    public boolean registry(String hostname, Integer port) {
        System.out.println("registry to zookeeper, hostname is " + hostname + ",port is " + port);
        return false;
    }
}
