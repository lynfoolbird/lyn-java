package com.lynjava.rpc.client.balancer.impl;


import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.core.consts.LoadBalancerTypeEnum;
import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;

/**
 * 轮询算法
 */
public class RoundRobinBalancer implements ILoadBalancer {
    private int index;

    @Override
    public synchronized ServiceInfo chooseOne(List<ServiceInfo> services) {
        // 加锁防止多线程情况下，index超出services.size()
        if (index >= services.size()) {
            index = 0;
        }
        return services.get(index++);
    }

    @Override
    public String getUniqueCode() {
        return LoadBalancerTypeEnum.ROUND_ROBIN.getCode();
    }
}
