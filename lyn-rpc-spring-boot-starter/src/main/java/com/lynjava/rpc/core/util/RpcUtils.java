package com.lynjava.rpc.core.util;

import java.util.UUID;

public final class RpcUtils {
    private RpcUtils() {}

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String serviceKey(String serviceName, String version) {
        return String.join("-", serviceName, version);
    }
}
