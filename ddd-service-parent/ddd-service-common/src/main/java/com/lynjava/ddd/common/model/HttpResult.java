package com.lynjava.ddd.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResult {
    private boolean success;
    private int code;
    private Object data;
    private String message;
}
