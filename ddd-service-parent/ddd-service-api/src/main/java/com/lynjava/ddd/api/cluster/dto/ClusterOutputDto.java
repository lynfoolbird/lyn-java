package com.lynjava.ddd.api.cluster.dto;

import com.lynjava.ddd.api.shared.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClusterOutputDto extends BaseDto {
    private Integer id;

    private String name;

    private String category;
}
