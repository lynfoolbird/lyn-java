package com.lynjava.ddd.persistence.cluster.dao;

import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import com.lynjava.ddd.persistence.config.DddBaseMapper;

/**
 * 单表crud操作用mybatisplus；
 * 生成代码不支持的再写sql
 */
public interface IClusterDao extends DddBaseMapper<ClusterPO> {

    Integer selectMaxAge();
}
