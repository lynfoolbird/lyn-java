package com.lynjava.rpc.client.balancer.impl;

import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平滑加权轮询
 */
public class SmoothWeightRoundBalancer implements ILoadBalancer {
    /**
     * key:服务value:当前权重
     */
    private static final Map<String, Integer> map = new HashMap<>();

    @Override
    public synchronized ServiceInfo chooseOne(List<ServiceInfo> services) {
        services.forEach(service ->
                map.computeIfAbsent(service.toString(), key -> service.getWeight())
        );
        ServiceInfo maxWeightServer = null;
        int allWeight = services.stream().mapToInt(ServiceInfo::getWeight).sum();
        for (ServiceInfo service : services) {
            Integer currentWeight = map.get(service.toString());
            if (maxWeightServer == null || currentWeight > map.get(maxWeightServer.toString())) {
                maxWeightServer = service;
            }
        }

        assert maxWeightServer != null;

        map.put(maxWeightServer.toString(), map.get(maxWeightServer.toString()) - allWeight);

        for (ServiceInfo service : services) {
            Integer currentWeight = map.get(service.toString());
            map.put(service.toString(), currentWeight + service.getWeight());
        }
        return maxWeightServer;
    }

    @Override
    public String getUniqueCode() {
        return "SmoothWeightRound";
    }
}
