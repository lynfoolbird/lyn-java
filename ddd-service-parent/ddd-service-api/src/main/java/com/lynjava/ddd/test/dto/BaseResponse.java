package com.lynjava.ddd.test.dto;

import java.io.Serializable;

/**
 * Created by liyanan on 2018/6/10.
 */
public class BaseResponse implements Serializable {
    private int code;
    private Object data;
    private String message;

    public BaseResponse(){
    }
    public BaseResponse(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
