package com.lynjava.ddd.common.consts;

public interface RedisKeyConsts {

    /**
     * 路由策略key，格式：router.路由id.policy.策略id
     */
    static String getRouterPolicyKey(String routerId, String policyId) {
        return String.format( "router.%s.policy.$5s", routerId, policyId);
    }

}
