package com.lynjava.ddd.common.consts;

/**
 * 枚举使用模板
 */
public enum UserRoleEnum {
    PARENT(1, "家长"){
        @Override
        public Object transData(Object srcData) {
            System.out.println("parent.value = "+this.getValue());
            return "parent:" + srcData;
        }
    },
    STUDENT(2, "学生"){
        @Override
        public Object transData(Object srcData) {
            return "student:" + srcData;
        }
    };

    private int value;
    private String desc;

    UserRoleEnum(int value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public Object transTemplate(Object srcData){
        System.out.println("do common trans. value=" + this.value);
        System.out.println("do custom trans. value=" + this.value);
        return transData(srcData);
    }
    public abstract Object transData(Object srcData);

    public static UserRoleEnum getByValue(Integer value){
        if (null == value){
            return null;
        }
        for (UserRoleEnum userIdentityEnum : UserRoleEnum.values()){
            if (value.equals(userIdentityEnum.getValue())){
                return userIdentityEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}


