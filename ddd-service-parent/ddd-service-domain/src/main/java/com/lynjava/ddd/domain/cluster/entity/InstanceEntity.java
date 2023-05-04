package com.lynjava.ddd.domain.cluster.entity;

import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;

/**
 * 聚合内实体：实例
 */
@Data
public class InstanceEntity extends BaseEntity {
    private String hostname;

    private Integer port;
}
