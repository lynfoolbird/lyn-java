package com.lynjava.ddd.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Serializable {

    private String id;

    private String isDeleted;

    private Date createdDate;

    private String createdBy;

    private Date lastUpdatedDate;

    private String lastUpdatedBy;
}
