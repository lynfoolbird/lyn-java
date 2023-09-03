package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class PluginRegistry {
    private static Map<String, IPlugin> PLUGIN_MAP = new ConcurrentHashMap<>();

    // SPI方式加载所有插件实现类
   static {
       ServiceLoader<IPlugin> loader = ServiceLoader.load(IPlugin.class);
       for (IPlugin plugin : loader) {
           PLUGIN_MAP.putIfAbsent(plugin.getClass().getName(), plugin);
       }
   }

    public static List<IPlugin> getCommonPlugins() {
        List<IPlugin> plugins = new ArrayList<>();
        plugins.add(getPlugin("com.lynjava.ddd.test.architecture.designpattern.chain.list.EndPointPlugin")); // 黑白名单
        return plugins;
    }

    public static IPlugin getPlugin(String pluginClass) {
        return PLUGIN_MAP.get(pluginClass);
    }
}
