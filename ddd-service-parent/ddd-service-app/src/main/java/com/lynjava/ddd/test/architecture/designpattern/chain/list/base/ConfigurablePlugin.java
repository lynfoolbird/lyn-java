package com.lynjava.ddd.test.architecture.designpattern.chain.list.base;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;

public interface ConfigurablePlugin extends IPlugin {
    Class<? extends BasePluginConfig> configClass();

    @SuppressWarnings("all")
    default <T extends BasePluginConfig> T getConfig(GatewayContext context) {
        return (T) context.getConfig().get(this.getClass().getName());
    }
}
