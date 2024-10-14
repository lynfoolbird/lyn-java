package com.lynjava.ddd.test.architecture.designpattern.chain.list.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor.Processor;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasePluginConfig implements Serializable {

    /** 是否开启 */
    protected Boolean enabled;

    /**
     * 插件处理器
     */
    private List<Processor> preProcessors;

    private List<Processor> postProcessors;

    public Boolean enabled() {
        return enabled != null && enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
