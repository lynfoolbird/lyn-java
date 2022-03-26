package com.lynjava.ddd.domain.cluster;

import com.lynjava.ddd.common.model.BaseEntity;
import com.lynjava.ddd.domain.cluster.entity.InstanceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 集群聚合根
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClusterAR extends BaseEntity {

    private String name;

    private String category;

    private List<InstanceEntity> instances;

}
