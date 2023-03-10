package com.lynjava.ddd.common.consts;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 枚举使用模板
 */
public enum DemoEnum {
    /**
     * 父亲
     */
    PARENT("PARENT", "家长") {
        @Override
        public Object transData(Object srcData) {
            System.out.println("parent.code = "+this.getCode());
            return "parent:" + srcData;
        }
    },
    STUDENT("STUDENT", "学生") {
        @Override
        public Object transData(Object srcData) {
            return "student:" + srcData;
        }
    };

    /**
     * 缓存映射信息
     */
    private final static Map<String, DemoEnum> CACHE = new HashMap<>();
    static {
        for (DemoEnum demoEnum : DemoEnum.values()) {
            CACHE.put(demoEnum.getCode(), demoEnum);
        }
    }

    private String code;
    private String desc;

    DemoEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public Object transTemplate(Object srcData){
        System.out.println("do common trans. code=" + this.code);
        System.out.println("do custom trans. code=" + this.code);
        return transData(srcData);
    }
    public abstract Object transData(Object srcData);

    public static DemoEnum getByCode(String code){
        if (null == code){
            return null;
        }
        for (DemoEnum demoEnum : DemoEnum.values()) {
            if (Objects.equals(code, demoEnum.getCode())) {
                return demoEnum;
            }
        }
        return null;
    }

    public static DemoEnum getByCode2(String code) {
        return CACHE.get(code);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}


