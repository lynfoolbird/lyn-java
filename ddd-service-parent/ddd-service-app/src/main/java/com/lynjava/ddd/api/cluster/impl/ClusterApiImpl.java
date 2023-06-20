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
    public Result<ClusterOutputDto> getCluster(Integer id) {
        ClusterOutputDto outputDto = clusterAppService.queryClusterById(id);
        return Result.success(outputDto);
    }

    @Override
    public Result listByPage(Integer curPage, Integer pageSize, ClusterInputDto queryParam) {
        return Result.success(clusterAppService.listByPage(curPage, pageSize, queryParam.getCategory()));
    }

    @Override
    public Result createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterApiImpl: " + "createCluster");
        clusterAppService.createCluster(clusterInputDto);
        return Result.success();
    }

    @Override
    public Result updateCluster(Integer clusterId, ClusterInputDto clusterInputDto) {
        return Result.success(clusterAppService.updateCluster(clusterId, clusterInputDto));
    }

    @Override
    public Result patchCluster(Integer clusterId, String type, String body) {
        return Result.success(clusterAppService.patchCluster(clusterId, type, body));
    }

    @Override
    public Result deleteCluster(Integer clusterId) {
        return Result.success();
    }

    @Override
    @DataScopeLimit(resourceIdSpel = "#clusterList.![id]", resourceType = "CLUSTER_ID", rights = {"write"})
    public Result batchDeleteCluster(List<ClusterInputDto> clusterList) {
        return Result.success();
    }
}
