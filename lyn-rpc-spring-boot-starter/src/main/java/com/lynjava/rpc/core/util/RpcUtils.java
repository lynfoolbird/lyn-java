package com.lynjava.rpc.core.util;

import java.util.UUID;

public final class RpcUtils {
    private RpcUtils() {}

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
