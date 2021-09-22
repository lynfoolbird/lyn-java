package com.lynjava.ddd.api.cluster.impl;

import com.lynjava.ddd.api.cluster.IClusterApi;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.app.cluster.service.ClusterAppService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * 面向业务功能，编排app层服务
 */
@Named
public class ClusterApiImpl implements IClusterApi {

    @Inject
    private ClusterAppService clusterAppService;

    @Override
    public Object getCluster(String id, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put(id, clusterAppService.getCluster());
        map.put("type", type);
        return map;
    }

    @Override
    public Object createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterApiImpl: " + "createCluster");
        return clusterAppService.createCluster(clusterInputDto);
    }

    @Override
    public Object updateCluster(String clusterId, ClusterInputDto clusterInputDto) {
        return null;
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
