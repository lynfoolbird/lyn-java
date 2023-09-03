package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.ApplyContext;

public interface IPlugin {
    void invoke(ApplyContext context) throws Exception;
}
