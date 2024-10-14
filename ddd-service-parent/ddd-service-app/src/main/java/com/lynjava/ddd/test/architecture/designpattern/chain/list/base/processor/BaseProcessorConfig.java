package com.lynjava.ddd.test.architecture.designpattern.chain.list.base.processor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseProcessorConfig implements Serializable {

    /** 是否开启 */
    protected Boolean enabled;

    public Boolean enabled() {
        return enabled != null && enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
