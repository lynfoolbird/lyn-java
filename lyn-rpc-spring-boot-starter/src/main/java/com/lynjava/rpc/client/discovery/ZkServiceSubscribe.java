package com.lynjava.rpc.client.discovery;

import com.lynjava.rpc.client.balancer.ILoadBalancer;
import com.lynjava.rpc.core.consts.RpcConstants;
import com.lynjava.rpc.core.model.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用zk作注册中心：服务发现
 */
@Slf4j
public class ZkServiceSubscribe implements IServiceSubscribe {
    private ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ILoadBalancer loadBalancer;

    public ZkServiceSubscribe(String registryAddress, ILoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddress,
                    new ExponentialBackoffRetry(RpcConstants.ZK.CONNECT_BASE_SLEEP_TIME_MS, RpcConstants.ZK.CONNECT_MAX_RETRIES));
            client.start();
            JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
            this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                    .client(client)
                    .serializer(serializer)
                    .basePath(RpcConstants.ZK.REGISTRY_BASE_PATH)
                    .build();
            this.serviceDiscovery.start();
        } catch (Exception e) {
            log.error("serviceDiscovery start error :", e);
        }
    }

    @Override
    public ServiceInfo discovery(String serviceKey) throws Exception {
        Collection<ServiceInstance<ServiceInfo>> serviceInstances = serviceDiscovery
                .queryForInstances(serviceKey);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return null;
        }
        return loadBalancer.chooseOne(serviceInstances.stream().map(ServiceInstance::getPayload).collect(Collectors.toList()));
    }

    @Override
    public List<ServiceInfo> findAll(String serviceKey) throws Exception {
        Collection<ServiceInstance<ServiceInfo>> serviceInstances = serviceDiscovery
                .queryForInstances(serviceKey);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return Collections.EMPTY_LIST;
        }
        return serviceInstances.stream().map(ServiceInstance::getPayload)
                .collect(Collectors.toList());
    }

}
