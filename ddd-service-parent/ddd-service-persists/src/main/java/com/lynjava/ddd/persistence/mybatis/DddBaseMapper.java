package com.lynjava.ddd.persistence.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DddBaseMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(List<T> entities);
}
