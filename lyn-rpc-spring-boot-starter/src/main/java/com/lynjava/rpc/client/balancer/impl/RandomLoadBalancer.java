package com.lynjava.rpc.client.balancer.impl;

import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.core.consts.LoadBalancerTypeEnum;
import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 */
public class RandomLoadBalancer implements ILoadBalancer {
    private static Random random = new Random();

    @Override
    public ServiceInfo chooseOne(List<ServiceInfo> services) {
        return services.get(random.nextInt(services.size()));
    }

    @Override
    public String getUniqueCode() {
        return LoadBalancerTypeEnum.RANDOM.getCode();
    }
}
