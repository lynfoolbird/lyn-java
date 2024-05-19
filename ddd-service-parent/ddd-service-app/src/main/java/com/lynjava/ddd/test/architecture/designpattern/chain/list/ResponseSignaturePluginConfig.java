package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.BasePluginConfig;
import lombok.Data;

@Data
public class ResponseSignaturePluginConfig extends BasePluginConfig  {

    private String signType;

    private String remoteUrl;




}
