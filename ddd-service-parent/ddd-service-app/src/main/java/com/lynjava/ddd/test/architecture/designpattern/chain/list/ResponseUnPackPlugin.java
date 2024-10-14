package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.InvokeResult;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.BasePluginConfig;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.ConfigurablePlugin;

/**
 * 响应报文拆组插件
 */
public class ResponseUnPackPlugin implements ConfigurablePlugin {
    @Override
    public Class<? extends BasePluginConfig> configClass() {
        return PackagePluginConfig.class;
    }

    @Override
    public InvokeResult invoke(GatewayContext context, Chain chain) throws Exception {
        // 后置插件是先调用chain.proceed()方法再执行插件业务逻辑
        InvokeResult result = chain.proceed();
        // 获取插件配置
        PackagePluginConfig config = this.getConfig(context);
        return null;
    }
}
