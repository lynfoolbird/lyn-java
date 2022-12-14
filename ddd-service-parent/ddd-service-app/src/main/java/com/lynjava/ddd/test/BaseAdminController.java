package com.lynjava.ddd.test;


import com.lynjava.ddd.common.model.BaseResponse;
import com.lynjava.ddd.common.model.BaseResponseCode;

/**
 * Created by liyanan on 2018/6/16.
 */
public abstract  class BaseAdminController {
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "success";

    public BaseResponse msgResponse(BaseResponseCode responseCode) {
        return new BaseResponse(responseCode.getCode(), responseCode.getMessage());
    }

    public  BaseResponse successMsgResponse(){
        return new BaseResponse(SUCCESS_CODE, SUCCESS_MSG);
    }

    public  BaseResponse successDataResponse(Object data) {
        return new BaseResponse(SUCCESS_CODE, data, SUCCESS_MSG);
    }

    public BaseResponse msgFormatResponse(BaseResponseCode response, Object...args) {
        return new BaseResponse(response.getCode(), String.format(response.getMessage(), args));
    }
}
