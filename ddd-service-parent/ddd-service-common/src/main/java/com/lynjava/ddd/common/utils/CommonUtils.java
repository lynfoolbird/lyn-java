package com.lynjava.ddd.common.utils;

import java.util.UUID;

/**
 * @author li
 */
public final class CommonUtils {

    public static String getId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").toUpperCase();
    }

}
