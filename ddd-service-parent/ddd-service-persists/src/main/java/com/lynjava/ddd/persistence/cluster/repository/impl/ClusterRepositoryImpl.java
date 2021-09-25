package com.lynjava.ddd.persistence.cluster.repository.impl;

import com.lynjava.ddd.domain.cluster.repository.IClusterRepository;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;

import javax.inject.Named;

@Named
public class ClusterRepositoryImpl implements IClusterRepository {

    @Override
    public int createCluster(ClusterPO clusterPO) {
        System.out.println("ClusterRepositoryImpl:" + "createCluster");
        return 0;
    }
}
