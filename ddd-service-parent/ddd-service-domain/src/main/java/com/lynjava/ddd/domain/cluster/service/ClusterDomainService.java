package com.lynjava.ddd.domain.cluster.service;


import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.factory.ClusterFactory;
import com.lynjava.ddd.domain.cluster.repository.IClusterRepository;
import com.lynjava.ddd.domain.cluster.vo.OrderVO;
import com.lynjava.ddd.domain.external.IServiceMarketExternalService;

import javax.inject.Inject;
import javax.inject.Named;

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
        OrderVO orderVO = OrderVO.builder()
                .orderId("one")
                .orderName("computer")
                .quantity(100)
                .build();
        System.out.println(serviceMarketExternalService.createOrder(orderVO));
        return "succ";
    }

}
