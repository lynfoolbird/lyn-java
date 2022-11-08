package com.lynjava.ddd.common.consts;

/**
 * 配置项key
 * 若放在一个类中过多，则可公共、子域拆分
 * 格式：子域.功能点.*
 */
public enum PropertyKeyEnum {
    /**
     * 集群-是否启用功能01
     */
    CLUSTER_FUNCTION_01_ENABLE("cluster.function01.enable"),

    /**
     * 路由-文根数上限
     */
    ROUTER_CONTEXT_UPLIMIT( "router.context.uplimit");

    private String value;

    PropertyKeyEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 拼接key
     *
     * @param params
     * @return
     */
    public String getPropertyKey(Object... params) {
        return String.format(this.value, params);
    }
}


