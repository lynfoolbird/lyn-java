package com.lynjava.ddd.domain.cluster;

import com.lynjava.ddd.common.model.BaseEntity;
import com.lynjava.ddd.domain.cluster.entity.InstanceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聚合根：集群
 * 封装行为、充血模型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClusterAR extends BaseEntity {

    private String name;

    private String category;

    private List<InstanceEntity> instances;

    public void check() {

    }

    public Map<String, Object> buildRedisValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

}
