package com.lynjava.ddd.common.context;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    public static Object get(String key) {
        return THREAD_LOCAL.get().get(key);
    }


    public static void setTxnTimeout(Integer txnTimeoutMills) {
        set("TXN_TIMEOUT", txnTimeoutMills);
    }

    public static Integer getTxnTimeout() {
        return (Integer) get("TXN_TIMEOUT");
    }

    public static void clear() {
        THREAD_LOCAL.get().clear();
    }
}
