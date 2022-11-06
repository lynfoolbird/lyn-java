package com.lynjava.ddd.common.consts;

/**
 * 枚举使用模板
 */
public enum UserRoleEnum {
    PARENT(1, "家长"){
        @Override
        public Object transData(Object srcData) {
            System.out.println("parent.code = "+this.getCode());
            return "parent:" + srcData;
        }
    },
    STUDENT(2, "学生"){
        @Override
        public Object transData(Object srcData) {
            return "student:" + srcData;
        }
    };

    private int code;
    private String desc;

    UserRoleEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public Object transTemplate(Object srcData){
        System.out.println("do common trans. code=" + this.code);
        System.out.println("do custom trans. code=" + this.code);
        return transData(srcData);
    }
    public abstract Object transData(Object srcData);

    public static UserRoleEnum getByValue(Integer value){
        if (null == value){
            return null;
        }
        for (UserRoleEnum userIdentityEnum : UserRoleEnum.values()){
            if (value.equals(userIdentityEnum.getCode())){
                return userIdentityEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}


