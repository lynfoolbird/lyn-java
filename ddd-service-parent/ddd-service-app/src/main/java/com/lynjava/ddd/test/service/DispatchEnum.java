package com.lynjava.ddd.test.service;

import com.lynjava.ddd.common.context.DddAppContext;

import java.util.HashMap;
import java.util.Map;
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
            ITestService testService = DddAppContext.getContext().getBean(getComponentName(), ITestService.class);
            return testService.sayHello((String) params[0]);
        }
    };

    private String componentName;
    private String methodName;

    private static final Map<String, DispatchEnum> CACHE = new HashMap<>();
    static {
        for (DispatchEnum dispatchEnum : DispatchEnum.values()) {
            String key = dispatchEnum.componentName + ":" + dispatchEnum.getMethodName();
            CACHE.put(key, dispatchEnum);
        }
    }

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

    /**
     * getByCompMethod
     *
     * @param componentName
     * @param methodName
     * @return
     */
    public static DispatchEnum getByCompMethod2(String componentName, String methodName){
        String key = componentName + ":" + methodName;
        return CACHE.get(key);
    }

    public String getComponentName() {
        return componentName;
    }

    public String getMethodName() {
        return methodName;
    }
}


