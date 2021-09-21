package com.lynjava.ddd.domain.cluster;

import com.lynjava.ddd.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClusterAR extends BaseEntity {

    private String name;

}
