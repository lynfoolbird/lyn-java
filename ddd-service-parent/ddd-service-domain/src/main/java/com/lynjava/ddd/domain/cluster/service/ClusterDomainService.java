package com.lynjava.ddd.domain.cluster.service;


import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.factory.ClusterFactory;
import com.lynjava.ddd.domain.cluster.repository.IClusterRepository;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * 集群子域-领域服务
 */
@Named
public class ClusterDomainService {

    @Inject
    private ClusterFactory clusterFactory;

    @Inject
    private IClusterRepository clusterRepository;

    @Inject
    private IServiceMarketExternalService serviceMarketExternalService;

    public ClusterAR getCluster() {
        ClusterAR clusterAR = ClusterAR.builder().name("clustername").build();
        return clusterAR;
    }

    public String createCluster(ClusterAR clusterAR) {
        System.out.println("ClusterDomainService:" + "createCluster");
        clusterRepository.createCluster(clusterFactory.toPO(clusterAR));
        ClusterAR cluster = serviceMarketExternalService.createOrder(clusterAR);
        System.out.println("success");
        return "succ";
    }

    public ClusterAR getById(int id) {
        ClusterPO clusterPO = clusterRepository.getById(id);
        return clusterFactory.toDO(clusterPO);
    }

}
