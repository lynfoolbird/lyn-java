package com.lynjava.ddd.common.utils;

import java.util.UUID;

/**
 * Created by liyanan on 2018/5/26.
 */
public final class UUIDUtils {

    public static String getId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").toUpperCase();
    }
}
