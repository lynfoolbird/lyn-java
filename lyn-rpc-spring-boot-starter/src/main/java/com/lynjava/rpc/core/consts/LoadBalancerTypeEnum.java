package com.lynjava.rpc.core.consts;

import lombok.Getter;

public enum LoadBalancerTypeEnum {
    /**
     * 随机
     */
    RANDOM("Random"),
    /**
     * 轮询
     */
    ROUND_ROBIN("RoundRobin"),
    /**
     * 加权轮询
     */
    WEIGHT_ROUND("WeightRound");

    @Getter
    private String code;

    LoadBalancerTypeEnum(String code) {
        this.code = code;
    }

    public static LoadBalancerTypeEnum getByCode(String code) {
        for (LoadBalancerTypeEnum typeEnum : LoadBalancerTypeEnum.values()) {
            if (typeEnum.getCode().equalsIgnoreCase(code)) {
                return typeEnum;
            }
        }
        return RANDOM;
    }
}
