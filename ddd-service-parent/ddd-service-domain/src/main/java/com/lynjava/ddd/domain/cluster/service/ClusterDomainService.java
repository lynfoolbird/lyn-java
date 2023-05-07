package com.lynjava.ddd.domain.cluster.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lynjava.ddd.common.context.DddRequestContext;
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

    public String createCluster(ClusterAR clusterAR) {
        System.out.println("ClusterDomainService:" + "createCluster");
        ClusterPO clusterPO = clusterFactory.toPO(clusterAR);
        clusterRepository.createCluster(clusterPO);
        // 保存后orm框架回填主键id
        Integer clusterId = clusterPO.getId();
        String operateBatchId = (String) DddRequestContext.getAttribute("operateBatchId");
        ClusterAR cluster = serviceMarketExternalService.createOrder(clusterAR);
        System.out.println("success");
        return "succ";
    }

    public ClusterAR getById(int id) {
        ClusterPO clusterPO = clusterRepository.getById(id);
        return clusterFactory.toDO(clusterPO);
    }

    public IPage listByPage(IPage page, String category) {
        return clusterRepository.listByPage(page, category);
    }
}
