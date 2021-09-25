package com.lynjava.ddd.api.cluster.dto;

import com.lynjava.ddd.api.shared.BaseDto;
import lombok.Data;

@Data
public class ClusterInputDto extends BaseDto {
    private String id;

    private String name;
}
