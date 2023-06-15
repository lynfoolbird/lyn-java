package com.lynjava.ddd.app.cluster.appservice.partial.impl;

import com.lynjava.ddd.app.cluster.appservice.partial.BaseClusterPartialService;

import javax.inject.Named;

@Named("PartBType")
public class PartBClusterPartialServiceImpl extends BaseClusterPartialService {
    @Override
    public Object process(Integer clusterId, String body) {
        // 反序列化得到body对象
        return null;
    }
}
