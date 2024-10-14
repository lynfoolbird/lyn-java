package com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor;

/**
 * 插件通用处理器
 */
public interface ConfigurableProcessor extends ICommonProcessor {

    Class<? extends BaseProcessorConfig> configClass();

}
