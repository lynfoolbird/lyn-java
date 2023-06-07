package com.lynjava.rpc.client.balancer;

import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;

/**
 * 负载均衡算法接口
 */
public interface ILoadBalancer {
    ServiceInfo chooseOne(List<ServiceInfo> services);
}
