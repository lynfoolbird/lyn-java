package com.lynjava.ddd.common.utils;

import java.util.UUID;

/**
 * Created by li
 */
public final class UUIDUtils {

    public static String getId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").toUpperCase();
    }
}
