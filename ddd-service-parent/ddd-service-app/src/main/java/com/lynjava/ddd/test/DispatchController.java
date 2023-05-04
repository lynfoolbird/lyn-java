package com.lynjava.ddd.test;

import com.lynjava.ddd.common.context.DddApp;
import com.lynjava.ddd.test.constant.DispatchEnum;
import lombok.Data;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求分发接口
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchController {
    @PostMapping("/demo1")
    public Object dispatch(@RequestBody DispatchInfoDto dto) {
        Object bean = DddApp.getContext().getBean(dto.getComponentName());
        Class<? extends Object>[] paramClass = null;
        Object[] params = dto.getParams();
        if (params != null) {
            int paramsLength = params.length;
            paramClass = new Class[paramsLength];
            for (int i = 0; i < paramsLength; i++) {
                paramClass[i] = params[i].getClass();
            }
        }
        // 找到方法
        Method method = ReflectionUtils.findMethod(bean.getClass(), dto.getMethodName(), paramClass);
        if (Objects.isNull(method)) {
            return "Not found target method.";
        }
        // 执行方法
        return ReflectionUtils.invokeMethod(method, bean, params);
    }

    @PostMapping("/demo2")
    public Object dispatch2(@RequestBody DispatchInfoDto dto) {
        DispatchEnum dispatchEnum = DispatchEnum.getByCompMethod(dto.getComponentName(), dto.getMethodName());
         if (Objects.isNull(dispatchEnum)) {
             return "Not found target method.";
         }
        return dispatchEnum.invoke(dto.getParams());
    }

    @Data
    public static class DispatchInfoDto {
        // 请求目标beanid
        private String componentName;
        // 请求目标方法名
        private String methodName;
        // 请求目标方法参数
        private Object[] params;
    }
}

