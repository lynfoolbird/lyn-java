package com.lynjava.ddd.app.cluster.appservice;


import com.lynjava.ddd.api.cluster.assembler.ClusterAssembler;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.app.cluster.appservice.partial.IClusterPartialService;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;
import com.lynjava.ddd.domain.external.iam.IamExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 功能：面向业务接口，编排需要调用的领域服务和防腐层接口
 */
@Named
public class ClusterAppService {

    @Inject
    private ClusterAssembler clusterAssembler;

    @Inject
    private ClusterDomainService clusterDomainService;

    @Inject
    private IamExternalService iamExternalService;

    @Autowired
    private ApplicationContext applicationContext;

    public ClusterOutputDto getCluster() {
        iamExternalService.printIam();
        return clusterAssembler.toOutputDto(clusterDomainService.getCluster());
    }

    public String createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterAppService: " + "createCluster");
        return clusterDomainService.createCluster(clusterAssembler.toDO(clusterInputDto));
    }

    public Object updateCluster(String id, String type, String body) {
        IClusterPartialService bean = applicationContext.getBean(type, IClusterPartialService.class);
        return bean.process(body);
    }
}
