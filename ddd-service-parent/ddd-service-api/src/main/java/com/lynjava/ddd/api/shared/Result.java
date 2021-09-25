package com.lynjava.ddd.api.shared;

import lombok.Data;

/**
 * 统一响应格式
 * 可放在项目级公共jar里面
 * @param <T>
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

//    private boolean success;

    private final static Integer CODE_SUCCESS = 200;
    private final static Integer COD_FAILURE = 500;

    // 构造器私有
    private Result(Integer code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(Integer code, String message){
        this(code, message, null);
    }

    public static Result success(){
        Result result = new Result(CODE_SUCCESS, "success");
        return result;
    }

    public static <T> Result<T> success(T data){
        return new Result(CODE_SUCCESS, "success", data);
    }

    public static Result failure() {
        return new Result(COD_FAILURE, "服务内部错误");
    }

    public static Result failure(Integer code, String message) {
        return new Result(code, message);
    }
}
