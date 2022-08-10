package com.lynjava.ddd.api.cluster.impl;

import com.lynjava.ddd.api.cluster.IClusterApi;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.app.cluster.service.ClusterAppService;
import com.lynjava.ddd.api.shared.Result;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 面向业务功能，编排app层服务
 */
@Named
public class ClusterApiImpl implements IClusterApi {

    @Value("${demokey:demovalue}")
    private String demokey;

    @Inject
    private ClusterAppService clusterAppService;

    @Override
    public Result<ClusterOutputDto> getCluster(String id, String type) {
        ClusterOutputDto outputDto = clusterAppService.getCluster();
        outputDto.setId(demokey);
        return Result.success(outputDto);
    }

    @Override
    public Object createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterApiImpl: " + "createCluster");
        return clusterAppService.createCluster(clusterInputDto);
    }

    @Override
    public Object updateCluster(String clusterId, String type, String body) {
        return clusterAppService.updateCluster(clusterId, type, body);
    }

    @Override
    public Object patchCluster(String clusterId, ClusterInputDto clusterInputDto) {
        return null;
    }

    @Override
    public Object deleteCluster(String clusterId) {
        return null;
    }
}
