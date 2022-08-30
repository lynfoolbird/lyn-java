package com.lynjava.ddd.api.router.dto;

import com.lynjava.ddd.api.shared.BaseDto;
import lombok.Data;

@Data
public class RouterInputDto extends BaseDto {
    private String id;

    private String name;
}
