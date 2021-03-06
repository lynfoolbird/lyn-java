package com.lynjava.ddd.app.cluster.service;


import com.lynjava.ddd.app.cluster.assmbler.ClusterAssmbler;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;
import com.lynjava.ddd.domain.external.iam.IamExternalService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 功能：面向业务接口，编排需要调用的领域服务和防腐层接口
 */
@Named
public class ClusterAppService {

    @Inject
    private ClusterAssmbler clusterAssmbler;

    @Inject
    private ClusterDomainService clusterDomainService;

    @Inject
    private IamExternalService iamExternalService;

    public ClusterOutputDto getCluster() {
        iamExternalService.printIam();
        return clusterAssmbler.toOutputDto(clusterDomainService.getCluster());
    }

    public String createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterAppService: " + "createCluster");
        return clusterDomainService.createCluster(clusterAssmbler.toDO(clusterInputDto));
    }
}
