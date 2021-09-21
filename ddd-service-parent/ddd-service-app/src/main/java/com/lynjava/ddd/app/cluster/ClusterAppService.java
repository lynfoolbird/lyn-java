package com.lynjava.ddd.app.cluster;


import com.lynjava.ddd.app.cluster.assmbler.ClusterAssmbler;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;

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

    public ClusterOutputDto getCluster() {
        return clusterAssmbler.toOutputDto(clusterDomainService.getCluster());
    }

    public String createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterAppService: " + "createCluster");
        return clusterDomainService.createCluster(clusterAssmbler.toDO(clusterInputDto));
    }
}
