package com.lynjava.ddd.test.strategy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainOperateService {

    private Map<String, IOperateService> operateMap = new HashMap<String,IOperateService>();

    /**
     * 利用依赖注入
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
            System.out.println("no service");
            return;
        }
        operateService.doSomething();
    }

}
