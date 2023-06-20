package com.lynjava.ddd.domain.router;

import com.lynjava.ddd.domain.router.entity.ServerGroup;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RouterAR routerAR = (RouterAR) o;
        return context.equals(routerAR.context) && serverGroups.equals(routerAR.serverGroups) && clusterIds.equals(routerAR.clusterIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), context, serverGroups, clusterIds);
    }
}
