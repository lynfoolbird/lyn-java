package com.lynjava.ddd.app.cluster.appservice.partial;

import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;

import javax.inject.Inject;
import javax.inject.Named;

@Named("PartAType")
public class PartAClusterPartialServiceImpl implements IClusterPartialService {

    @Inject
    private ClusterDomainService clusterDomainService;

    @Override
    public Object process(String body) {
        // 反序列化得到body对象
        return null;
    }
}
