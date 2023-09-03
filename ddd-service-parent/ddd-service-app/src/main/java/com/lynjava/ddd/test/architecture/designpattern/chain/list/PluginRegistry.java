package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginRegistry {
    private static Map<String, IPlugin> PLUGIN_MAP = new ConcurrentHashMap<>();

   static {
       System.out.println("");
   }
}
