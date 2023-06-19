package com.lynjava.ddd.app.cluster.appservice;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lynjava.ddd.api.cluster.assembler.ClusterAssembler;
import com.lynjava.ddd.api.cluster.assembler.ClusterMapper;
import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.app.cluster.appservice.partial.IClusterPartialService;
import com.lynjava.ddd.common.consts.RootConstants;
import com.lynjava.ddd.common.context.DddRequestContext;
import com.lynjava.ddd.common.exception.AppException;
import com.lynjava.ddd.common.context.DddAppContext;
import com.lynjava.ddd.common.other.ILynRpcDemoService;
import com.lynjava.ddd.common.utils.CommonUtils;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.cluster.service.AsyncService;
import com.lynjava.ddd.domain.cluster.service.ClusterDomainService;
import com.lynjava.ddd.domain.external.iam.IamExternalService;

import com.lynjava.rpc.core.annotation.LynRpcAutowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

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
    private ClusterMapper clusterMapper;

    @Inject
    private ClusterDomainService clusterDomainService;

    @Inject
    private IamExternalService iamExternalService;

    @LynRpcAutowired
    private ILynRpcDemoService lynRpcDemoService;

    /**
     * 依赖注入：setter注入map
     */
    @Inject
    private Map<String, IClusterPartialService> partialServiceMap;

    @Autowired
    private ApplicationContext applicationContext;

    @Inject
    private AsyncService asyncService;

    @Inject
    private ExecutorService resourceUploadPool;

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

    public ClusterOutputDto queryClusterById(int id) {
        System.out.println("queryClusterById:" + Thread.currentThread().getName());
        ListenableFuture<String> asyncResult = asyncService.print();
        asyncResult.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex.getMessage() + ":" + Thread.currentThread().getName());
            }
            @Override
            public void onSuccess(String result) {
                System.out.println(result + ":" + Thread.currentThread().getName());
            }
        });
        resourceUploadPool.execute(() -> {
            System.out.println("resourceUpload: " + Thread.currentThread().getName());
        });
        return clusterAssembler.toOutputDto(clusterDomainService.getById(id));
    }

    public IPage listByPage(int curPage, int pageSize, String category) {
        IPage page = new Page(curPage, pageSize);
        return clusterDomainService.listByPage(page, category);
    }

    public String createCluster(ClusterInputDto clusterInputDto) {
        System.out.println("ClusterAppService: " + "createCluster");
        ClusterAR clusterAR = clusterMapper.toDO(clusterInputDto);
        String str = lynRpcDemoService.doSomething("127.0.0.1", 999);
        if (!Objects.equals(clusterInputDto.getCategory(), "MASTER")) {
            throw new AppException("CLUSTER0001", "category not support.");
        }
        if (Objects.equals(RootConstants.SWITCH_ON, clusterIamEnable)) {
            iamExternalService.printIam(clusterAssembler.toDO(clusterInputDto));
        }
        // 操作批次放到请求上下文中
        DddRequestContext.addAttribute("operateBatchId", CommonUtils.getId());
        return clusterDomainService.createCluster(clusterAssembler.toDO(clusterInputDto));
    }

    public String updateCluster(Integer id, ClusterInputDto clusterInputDto) {
        clusterInputDto.setId(id);
        ClusterAR clusterAR = clusterAssembler.toDO(clusterInputDto);
        return clusterAR.toString();
    }

    public Object patchCluster(Integer id, String type, String body) {
        IClusterPartialService bean1 = applicationContext.getBean(type, IClusterPartialService.class);
        bean1.process(id, body);

        IClusterPartialService bean2 = DddAppContext.getContext().getBean(type, IClusterPartialService.class);
        bean2.process(id, body);

        IClusterPartialService bean3 = patchServiceMap.get(type);
        bean3.process(id, body);

        IClusterPartialService bean4 = partialServiceMap.get(type);
        return bean4.process(id, body);
    }
}
