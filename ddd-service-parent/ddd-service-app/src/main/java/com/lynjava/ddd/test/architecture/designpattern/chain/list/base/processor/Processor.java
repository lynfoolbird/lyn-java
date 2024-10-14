package com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor;

import lombok.Data;

@Data
public class Processor {

    private ICommonProcessor processor;

    private BaseProcessorConfig config;

    private Integer order;
}
