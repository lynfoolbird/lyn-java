package com.lynjava.ddd.api.cluster.impl;

import com.lynjava.ddd.api.cluster.IClusterApi;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.app.cluster.appservice.ClusterAppService;
import com.lynjava.ddd.api.shared.Result;
import com.lynjava.ddd.common.annotation.DataScopeLimit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * 面向业务功能，编排app层服务
 */
@Named
public class ClusterApiImpl implements IClusterApi {
    @Inject
    private ClusterAppService clusterAppService;

    @Override
    @DataScopeLimit(resourceIdSpel = "#id", resourceType = "CLUSTER_ID", rights = {"read"})
    public Result<ClusterOutputDto> getCluster(String id) {
        ClusterOutputDto outputDto = clusterAppService.getCluster();
        return Result.success(outputDto);
    }

    @Override
    public Result<List<ClusterOutputDto>> listByPage(Integer curPage, Integer pageSize, String type) {
        return null;
    }

    @Override
    public Result createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterApiImpl: " + "createCluster");
        clusterAppService.createCluster(clusterInputDto);
        return Result.success();
    }

    @Override
    public Result updateCluster(String clusterId, ClusterInputDto clusterInputDto) {
        return Result.success(clusterAppService.updateCluster(clusterId, clusterInputDto));
    }

    @Override
    public Result patchCluster(String clusterId, String type, String body) {
        return Result.success(clusterAppService.patchCluster(clusterId, type, body));
    }

    @Override
    public Result deleteCluster(String clusterId) {
        return Result.success();
    }

    @Override
    @DataScopeLimit(resourceIdSpel = "#clusterIds", resourceType = "CLUSTER_ID", rights = {"write"})
    public Result batchDeleteCluster(List<String> clusterIds) {
        return Result.success();
    }
}
