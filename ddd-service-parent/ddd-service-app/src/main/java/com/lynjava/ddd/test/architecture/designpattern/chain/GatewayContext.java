package com.lynjava.ddd.test.architecture.designpattern.chain;

import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.BasePluginConfig;
import lombok.Data;

import java.util.Map;

/**
 * 申请上下文
 */
@Data
public class GatewayContext {

    // 处理结果
    private Boolean success;

    private Map<String, BasePluginConfig> config;
}
