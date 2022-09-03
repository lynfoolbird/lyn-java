package com.lynjava.ddd.persistence.cluster.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface IClusterDao extends BaseMapper {

    Integer selectMaxAge();
}
