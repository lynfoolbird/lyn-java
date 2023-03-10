package com.lynjava.ddd.test.architecture.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MainOperateService {

    private Map<String, IOperateService> operateMap = new HashMap<String,IOperateService>();

    /**
     * 依赖注入：setter注入map
     */
    @Autowired
    private Map<String, IOperateService> oprMap;

    /**
     * 依赖注入：构造器注入list
     * @param operateServices
     */
    public MainOperateService(List<IOperateService> operateServices){
        for (IOperateService operateService:operateServices) {
            operateMap.put(operateService.getOperateType(), operateService);
        }
    }

    public void doOperate(String strategy) {
        IOperateService operateService = operateMap.get(strategy);
        if (null == operateService) {
            System.out.println("no appservice");
            return;
        }
        operateService.doSomething();
    }

    public void doOperate2(String strategy) {
        IOperateService operateService = oprMap.get(strategy);
        if (Objects.isNull(operateService)) {
            System.out.println("Not found operate service.");
            return;
        }
        operateService.doSomething();
    }

}
