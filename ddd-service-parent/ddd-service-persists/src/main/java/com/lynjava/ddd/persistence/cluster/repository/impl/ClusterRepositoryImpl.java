package com.lynjava.ddd.persistence.cluster.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    @Override
    public IPage<ClusterPO> listByPage(IPage page, String category) {
        QueryWrapper<ClusterPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClusterPO::getCategory, category)
                .orderByDesc(ClusterPO::getLastUpdatedDate)
                .orderByAsc(ClusterPO::getName);
        return clusterDao.selectPage(page, queryWrapper);
    }
}
