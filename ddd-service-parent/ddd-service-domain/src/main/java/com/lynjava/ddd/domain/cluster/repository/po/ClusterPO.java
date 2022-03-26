package com.lynjava.ddd.domain.cluster.repository.po;

import com.lynjava.ddd.common.model.BaseEntity;
import lombok.Data;

/**
 * 和db表对应
 */
@Data
public class ClusterPO extends BaseEntity {

    private String name;

}
