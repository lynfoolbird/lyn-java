package com.lynjava.rpc.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    private String requestId;
    /**
     * 请求的服务名
     */
    private String serviceName;
    /**
     * 请求调用的方法
     */
    private String method;

    private Map<String, String> headers = new HashMap<>();

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
