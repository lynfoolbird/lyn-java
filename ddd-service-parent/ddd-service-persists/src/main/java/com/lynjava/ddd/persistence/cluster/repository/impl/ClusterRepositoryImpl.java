package com.lynjava.ddd.persistence.cluster.repository.impl;

import com.lynjava.ddd.domain.cluster.repository.IClusterRepository;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import com.lynjava.ddd.persistence.cluster.dao.IClusterDao;
import com.lynjava.ddd.persistence.cluster.dao.IinstanceDao;

import javax.inject.Named;

@Named
public class ClusterRepositoryImpl implements IClusterRepository {

    private IClusterDao clusterDao;

    private IinstanceDao iinstanceDao;

    @Override
    public int createCluster(ClusterPO clusterPO) {
        System.out.println("ClusterRepositoryImpl:" + "createCluster");
        return 0;
    }
}
