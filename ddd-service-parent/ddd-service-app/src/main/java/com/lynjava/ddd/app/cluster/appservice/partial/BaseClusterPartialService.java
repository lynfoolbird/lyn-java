package com.lynjava.ddd.app.cluster.appservice.partial;

import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;

import javax.inject.Inject;

/**
 * 抽象类无需实现接口方法
 */
public abstract class BaseClusterPartialService implements IClusterPartialService {
    @Inject
    protected ClusterDomainService clusterDomainService;

    public void saveOperationLog(String action, ClusterAR clusterAR) {
        System.out.println("call operationLogDomainService");
    }
}
