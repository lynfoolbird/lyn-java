package com.lynjava.ddd.common.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private String errCode;

    public AppException() {
    }

    public AppException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public AppException(String message) {
        super(message);
    }
}
