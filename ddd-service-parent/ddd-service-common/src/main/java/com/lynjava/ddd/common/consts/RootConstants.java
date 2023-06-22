package com.lynjava.ddd.common.consts;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface RootConstants {

    String SUCCESS = "success";

    Map<String, Boolean> STRING_2_BOOLEAN = new HashMap<String, Boolean>(){
        {
            put("Y", true);
            put("N", false);
        }
    };

    Map<Boolean, String> BOOLEAN_2_STRING = new HashMap<Boolean, String>(){
        {
            put(true, "Y");
            put(false, "N");
        }
    };

    interface RegexPattern {
        Pattern EXTRACT_PARAMS = Pattern.compile("\\{[^}]+\\}");
    }

    /**
     * 利用内部类将常量分组
     */
    interface State {
        String VALID = "N";
        String INVALID = "Y";
        String ENABLE = "Y";
        String DISABLE = "N";
    }
}
