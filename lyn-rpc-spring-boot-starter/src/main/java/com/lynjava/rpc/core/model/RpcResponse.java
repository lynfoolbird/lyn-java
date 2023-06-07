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
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RpcResponse implements Serializable {

    private String requestId;

    private Map<String, String> headers = new HashMap<>();

    private Object data;

    private String message;

}
