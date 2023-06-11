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

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 使用zk作注册中心：服务发现
 */
@Slf4j
public class ZkServiceDiscovery implements IServiceDiscovery {
    private ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ILoadBalancer loadBalance;

    public ZkServiceDiscovery(String registryAddress, ILoadBalancer loadBalance) {
        this.loadBalance = loadBalance;
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddress,
                    new ExponentialBackoffRetry(RpcConstants.ZK_CONNECT_BASE_SLEEP_TIME_MS, RpcConstants.ZK_CONNECT_MAX_RETRIES));
            client.start();
            JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
            this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                    .client(client)
                    .serializer(serializer)
                    .basePath(RpcConstants.ZK_BASE_PATH)
                    .build();
            this.serviceDiscovery.start();
        } catch (Exception e) {
            log.error("serviceDiscovery start error :{}", e);
        }
    }


    /**
     *  服务发现
     * @param serviceName
     * @return ServiceInfo
     * @throws Exception
     */
    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        Collection<ServiceInstance<ServiceInfo>> serviceInstances = serviceDiscovery.queryForInstances(serviceName);
        if (Objects.isNull(serviceInstances) || serviceInstances.isEmpty()) {
            return null;
        }
        return loadBalance.chooseOne(serviceInstances.stream().map(ServiceInstance::getPayload).collect(Collectors.toList()));
    }

}
