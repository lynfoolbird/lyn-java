package com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessorRegistry {
    private static Map<String, ICommonProcessor> PROCESSOR_MAP = new ConcurrentHashMap<>();

   // SPI方式加载所有处理器实现类
   static {
       ServiceLoader<ICommonProcessor> loader = ServiceLoader.load(ICommonProcessor.class);
       for (ICommonProcessor processor : loader) {
           PROCESSOR_MAP.putIfAbsent(processor.getClass().getName(), processor);
       }
   }

    public static ICommonProcessor getProcessor(String processorClass) {
        return PROCESSOR_MAP.get(processorClass);
    }
}
