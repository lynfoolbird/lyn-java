package com.lynjava.ddd.domain.cluster.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;

import java.util.List;

/**
 * 集群聚合repo
 */
public interface IClusterRepository {

    int createCluster(ClusterPO clusterPO);

    ClusterPO getById(int id);

    IPage<ClusterPO> listByPage(IPage page, String category);

}
