package com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;

/**
 * 插件通用处理器
 */
public interface ICommonProcessor {

    <T> T process(GatewayContext context) throws RuntimeException;
}
