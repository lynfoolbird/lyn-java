package com.lynjava.rpc.client.balancer.impl;

import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.core.consts.LoadBalancerTypeEnum;
import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;

/**
 * 加权轮询
 */
public class WeightRoundBalancer implements ILoadBalancer {

    private static int index;

    @Override
    public synchronized ServiceInfo chooseOne(List<ServiceInfo> services) {
        int allWeight = services.stream().mapToInt(ServiceInfo::getWeight).sum();
        int number = (index++) % allWeight;
        for(ServiceInfo service : services){
            if (service.getWeight() > number){
                return service;
            }
            number -= service.getWeight();
        }
        return null;
    }

    @Override
    public String getUniqueCode() {
        return LoadBalancerTypeEnum.WEIGHT_ROUND.getCode();
    }
}
