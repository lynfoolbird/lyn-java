package com.lynjava.ddd.persistence.cluster.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;

public interface IClusterDao extends BaseMapper<ClusterPO> {

    Integer selectMaxAge();
}
