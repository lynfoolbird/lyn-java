package com.lynjava.ddd.domain.cluster.repository;

import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;

/**
 * 集群聚合repo
 */
public interface IClusterRepository {

    int createCluster(ClusterPO clusterPO);

    ClusterPO getById(int id);

}
