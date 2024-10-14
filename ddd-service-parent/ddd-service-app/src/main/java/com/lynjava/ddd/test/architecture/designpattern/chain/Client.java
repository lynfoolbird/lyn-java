package com.lynjava.ddd.test.architecture.designpattern.chain;

import com.lynjava.ddd.test.architecture.designpattern.chain.link.AbstractApproveHandler;
import com.lynjava.ddd.test.architecture.designpattern.chain.link.ConcreateLevel2ApproveHandler;
import com.lynjava.ddd.test.architecture.designpattern.chain.link.ConcreteLevel1ApproveHandler;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.RequestUnPackPlugin;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.ResponseUnPackPlugin;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.IPlugin;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.PluginRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 李亚南
 */
public class Client {
    public static void main(String[] args) throws Exception {
        AbstractApproveHandler handler = new AbstractApproveHandler.Builder()
                .addHandler(new ConcreteLevel1ApproveHandler())
                .addHandler(new ConcreateLevel2ApproveHandler())
                .build();
        handler.handler(new GatewayContext());

        List<IPlugin> plugins = new ArrayList<>();
        plugins.add(new RequestUnPackPlugin());
        plugins.add(new ResponseUnPackPlugin());
        List<IPlugin> commonPluginsPlugins = PluginRegistry.getCommonPlugins();
        plugins.addAll(commonPluginsPlugins);
        GatewayContext context = new GatewayContext();
        context.setConfig(new HashMap<>());
        IPlugin.Chain chain = new IPlugin.PluginChain(0, context, plugins);
        // list顺序：reqUnpack、reqPack、resPack、resUnpack、EndPoint；前置插件顺序添加，后置插件逆序添加
        // 插件执行顺序：reqUnpack、reqPack、EndPoint、resUnpack、resPack
        InvokeResult result = chain.proceed();
    }
}
