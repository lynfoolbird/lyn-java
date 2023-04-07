package com.lynjava.ddd.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RequestDataWrapper<T> {
    private String id;

    private String type;

    private Data<T> data;

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder(toBuilder = true)
    public static class Data<T> {
        private T attributes;
    }
}
