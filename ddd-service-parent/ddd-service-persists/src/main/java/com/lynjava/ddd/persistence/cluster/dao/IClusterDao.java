package com.lynjava.ddd.persistence.cluster.dao;

import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import com.lynjava.ddd.persistence.mybatis.DddBaseMapper;

public interface IClusterDao extends DddBaseMapper<ClusterPO> {

    Integer selectMaxAge();
}
