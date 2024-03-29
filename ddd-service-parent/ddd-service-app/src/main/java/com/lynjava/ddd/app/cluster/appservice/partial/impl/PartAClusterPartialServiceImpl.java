package com.lynjava.ddd.app.cluster.appservice.partial.impl;

import com.lynjava.ddd.app.cluster.appservice.partial.BaseClusterPartialService;
import com.lynjava.ddd.app.cluster.appservice.partial.ClusterPartialServiceContext;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named("PartAType")
public class PartAClusterPartialServiceImpl extends BaseClusterPartialService {

    @Inject
    private ClusterDomainService clusterDomainService;

    @PostConstruct
    public void register() {
        ClusterPartialServiceContext.registerClusterPartialService("PartAType", this);
    }

    @Override
    public Object process(Integer clusterId, String body) {
        // 反序列化得到body对象
        return null;
    }
}
