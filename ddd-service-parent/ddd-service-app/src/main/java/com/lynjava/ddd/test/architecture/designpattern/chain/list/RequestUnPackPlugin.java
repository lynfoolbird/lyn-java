package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.InvokeResult;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.BasePluginConfig;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.ConfigurablePlugin;

/**
 * 请求报文拆组插件
 *
 */
public class RequestUnPackPlugin implements ConfigurablePlugin {
    @Override
    public Class<? extends BasePluginConfig> configClass() {
        return PackagePluginConfig.class;
    }

    @Override
    public InvokeResult invoke(GatewayContext context, Chain chain) throws Exception {
        // 获取插件配置
        PackagePluginConfig config = this.getConfig(context);
        // 前置插件是先执行逻辑处理再调用chain.proceed()方法
        return chain.proceed();
    }
}
