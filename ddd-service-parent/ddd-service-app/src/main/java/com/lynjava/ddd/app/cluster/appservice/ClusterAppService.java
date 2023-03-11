package com.lynjava.ddd.app.cluster.appservice;


import com.lynjava.ddd.api.cluster.assembler.ClusterAssembler;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.app.cluster.appservice.partial.IClusterPartialService;
import com.lynjava.ddd.common.consts.CommonConstants;
import com.lynjava.ddd.common.exception.AppException;
import com.lynjava.ddd.common.utils.DddApp;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;
import com.lynjava.ddd.domain.external.iam.IamExternalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 功能：面向业务接口，编排需要调用的领域服务和防腐层接口
 */
@Named
public class ClusterAppService {
    @Value("${cluster.iam.enable:Y}")
    private String clusterIamEnable;

    @Inject
    private ClusterAssembler clusterAssembler;

    @Inject
    private ClusterDomainService clusterDomainService;

    @Inject
    private IamExternalService iamExternalService;

    /**
     * 依赖注入：setter注入map
     */
    @Inject
    private Map<String, IClusterPartialService> partialServiceMap;

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, IClusterPartialService> patchServiceMap = new HashMap<>();

    /**
     * 依赖注入：构造器注入list
     * @param list
     */
    public ClusterAppService(List<IClusterPartialService> list) {
        for (IClusterPartialService partialService : list) {
            patchServiceMap.put("", partialService);
        }
    }

    public ClusterOutputDto getCluster() {
        iamExternalService.printIam();
        return clusterAssembler.toOutputDto(clusterDomainService.getCluster());
    }

    public String createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterAppService: " + "createCluster");
        if (Objects.equals(CommonConstants.SWITCH_ON, clusterIamEnable)) {
            iamExternalService.printIam();
        }
        return clusterDomainService.createCluster(clusterAssembler.toDO(clusterInputDto));
    }

    public String updateCluster(String id, ClusterInputDto clusterInputDto) {
        clusterInputDto.setId(id);
        ClusterAR clusterAR = clusterAssembler.toDO(clusterInputDto);
        return clusterAR.toString();
    }

    public Object patchCluster(String id, String type, String body) {
        IClusterPartialService bean1 = applicationContext.getBean(type, IClusterPartialService.class);
        bean1.process(body);

        IClusterPartialService bean2 = DddApp.getContext().getBean(type, IClusterPartialService.class);
        bean2.process(body);

        IClusterPartialService bean3 = patchServiceMap.get(type);
        bean3.process(body);

        IClusterPartialService bean4 = partialServiceMap.get(type);
        if (Objects.isNull(bean4)) {
            throw new AppException("Not found service.");
        }
        return bean4.process(body);
    }
}
