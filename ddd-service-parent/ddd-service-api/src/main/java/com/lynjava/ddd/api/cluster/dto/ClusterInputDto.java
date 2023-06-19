package com.lynjava.ddd.api.cluster.dto;

import com.lynjava.ddd.api.shared.BaseDto;
import lombok.Data;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;

@Data
public class ClusterInputDto extends BaseDto {
    @Size(max = 10, message = "name less than 10", groups = {Insert.class, Update.class})
    private String name;

    private String category;

    public interface Insert extends Default {
    }
    public interface Update extends Default {
    }
}
