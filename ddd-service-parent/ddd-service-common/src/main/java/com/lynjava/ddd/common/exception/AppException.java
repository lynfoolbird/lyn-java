package com.lynjava.ddd.common.exception;

public class AppException extends RuntimeException {
    public AppException() {

    }

    public AppException(String message) {
        super(message);
    }
}
