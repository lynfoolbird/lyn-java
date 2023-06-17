package com.lynjava.ddd.common.exception;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * JAX-RS全局异常处理
 * @author li
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        if (ex instanceof AppException) {
            return buildResponse(Response.Status.OK, ((AppException) ex).getErrCode(), ex.getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            return buildResponse(Response.Status.BAD_REQUEST, "COMMON0002", ex.getMessage());
        } else if (ex instanceof ClientErrorException) {
            return buildResponse(((ClientErrorException) ex).getResponse().getStatusInfo().toEnum(),
                    "COMMON0004", ex.getMessage());
        } else {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "COMMON0003", ex.getMessage());
        }
    }

    private Response buildResponse(Response.Status status, String errCode, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("errCode", errCode);
        map.put("msg", msg);
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(map).build();
    }
}
