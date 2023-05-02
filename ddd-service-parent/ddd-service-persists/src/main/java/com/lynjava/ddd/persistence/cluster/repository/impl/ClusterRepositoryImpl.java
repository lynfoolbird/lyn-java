package com.lynjava.ddd.persistence.cluster.repository.impl;

import com.lynjava.ddd.domain.cluster.repository.IClusterRepository;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import com.lynjava.ddd.persistence.cluster.dao.IClusterDao;
import com.lynjava.ddd.persistence.cluster.dao.InstanceDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ClusterRepositoryImpl implements IClusterRepository {

    @Inject
    private IClusterDao clusterDao;

    @Inject
    private InstanceDao instanceDao;

    @Override
    public int createCluster(ClusterPO clusterPO) {
        System.out.println("ClusterRepositoryImpl:" + "createCluster begin");
        return clusterDao.insert(clusterPO);
    }

    @Override
    public ClusterPO getById(int id) {
        return clusterDao.selectById(id);
    }
}
