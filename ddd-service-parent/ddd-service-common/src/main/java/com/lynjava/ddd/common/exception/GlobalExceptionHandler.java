package com.lynjava.ddd.common.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;

/**
 * Spring全局异常处理
 * @author li
 */
@RestControllerAdvice
public class GlobalExceptionHandler  {
    @ExceptionHandler(Throwable.class)
    public Object handlerException(HttpServletRequest request, HttpServletResponse response,
                                          Throwable ex)
    {
        LynResponse lynResponse = toResponse(ex);
        response.setStatus(lynResponse.getHttpStatus());
        return lynResponse;
    }

    public LynResponse toResponse(Throwable ex) {
        if (ex instanceof AppException) {
            return LynResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .errCode(((AppException) ex).getErrCode())
                    .msg(ex.getMessage())
                    .build();
        } else if (ex instanceof ConstraintViolationException) {
            return LynResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .errCode("COMMON0002")
                    .msg(ex.getMessage())
                    .build();
        } else if (ex instanceof ClientErrorException) {
            return LynResponse.builder()
                    .httpStatus(((ClientErrorException) ex).getResponse().getStatusInfo().getStatusCode())
                    .errCode("COMMON0004")
                    .msg(ex.getMessage())
                    .build();
        } else {
            return LynResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errCode("COMMON0003")
                    .msg(ex.getMessage())
                    .build();
        }
    }

    @Data
    @Builder
    public static class LynResponse {
        private Integer httpStatus;
        private String errCode;
        private String msg;
    }
}
