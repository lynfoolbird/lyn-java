package com.lynjava.ddd.domain.router;

import com.lynjava.ddd.domain.router.entity.ServerGroup;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RouterAR extends BaseEntity {
    private String context;

    // 聚合内用对象引用
    private List<ServerGroup> serverGroups;

    // 聚合间用根id引用
    private List<Integer> clusterIds;

}
