package com.lynjava.rpc.client.balancer;

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
}
