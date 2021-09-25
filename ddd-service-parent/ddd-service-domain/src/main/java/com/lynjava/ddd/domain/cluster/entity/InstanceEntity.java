package com.lynjava.ddd.domain.cluster.entity;

import com.lynjava.ddd.common.model.BaseEntity;
import lombok.Data;

@Data
public class InstanceEntity extends BaseEntity {
    private String hostname;

    private Integer port;
}
