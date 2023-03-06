package com.lynjava.ddd.test.constant;

import com.lynjava.ddd.common.utils.DddApp;
import com.lynjava.ddd.test.service.ITestService;

import java.util.Objects;

/**
 * 枚举使用模板
 *
 * @author li
 */
public enum DispatchEnum {
    /**
     * SAY_HELLO
     */
    SAY_HELLO("testServiceImpl", "sayHello"){
        @Override
        public Object invoke(Object[] params) {
            ITestService testService = DddApp.getContext().getBean(getComponentName(), ITestService.class);
            return testService.sayHello((String) params[0]);
        }
    };

    private String componentName;
    private String methodName;

    DispatchEnum(String componentName, String methodName){
        this.componentName = componentName;
        this.methodName = methodName;
    }
    public abstract Object invoke(Object[] params);

    /**
     * getByCompMethod
     *
     * @param componentName
     * @param methodName
     * @return
     */
    public static DispatchEnum getByCompMethod(String componentName, String methodName){
        for (DispatchEnum dispatchEnum : DispatchEnum.values()){
            if (Objects.equals(componentName, dispatchEnum.getComponentName())
            && Objects.equals(methodName, dispatchEnum.getMethodName())){
                return dispatchEnum;
            }
        }
        return null;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getMethodName() {
        return methodName;
    }
}


