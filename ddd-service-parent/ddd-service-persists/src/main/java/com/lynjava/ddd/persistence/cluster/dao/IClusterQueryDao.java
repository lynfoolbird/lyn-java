package com.lynjava.ddd.persistence.cluster.dao;

import java.util.Map;

/**
 * 跨三个及以上聚合复杂查询
 */
public interface IClusterQueryDao  {

    Map listDetailClusters();
}
