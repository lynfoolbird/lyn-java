package com.lynjava.ddd.common.model;

import lombok.Data;

@Data
public class DataBaseOutputDto<T> {
    private String id;

    private String type;

    private T data;
}
