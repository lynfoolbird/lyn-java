package com.lynjava.ddd.domain.cluster.repository;

import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;

public interface IClusterRepository {

    int createCluster(ClusterPO clusterPO);

}
