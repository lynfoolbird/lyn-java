package com.lynjava.ddd.common.model;


public class BaseResponseCode  {
    private int code;
    private String message;

    public BaseResponseCode(){}

    public BaseResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final BaseResponseCode BACK_GROUND_ERROR = new BaseResponseCode(-1, "后台处理错误");
}
