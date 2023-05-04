package com.lynjava.ddd.api.shared;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDto implements Serializable {
    private Integer id;

    private String appId;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;
}
