package com.lynjava.ddd.domain.cluster.factory;

import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.repository.po.ClusterPO;
import org.springframework.beans.BeanUtils;

import javax.inject.Named;

@Named
public class ClusterFactory {

    public ClusterPO toPO(ClusterAR clusterAR) {
        ClusterPO clusterPO = new ClusterPO();
        BeanUtils.copyProperties(clusterAR, clusterPO);
        return clusterPO;
    }

    public ClusterAR toDO(ClusterPO clusterPO) {
        ClusterAR clusterAR = new ClusterAR();
        BeanUtils.copyProperties(clusterPO, clusterAR);
        return clusterAR;
    }
}
