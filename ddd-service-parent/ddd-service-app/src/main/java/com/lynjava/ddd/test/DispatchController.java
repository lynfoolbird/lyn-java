package com.lynjava.ddd.test;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * 请求分发接口
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/")
    public Object dispatch(@RequestBody DispatchInfoDto dto) {
        Object bean = applicationContext.getBean(dto.getComponentName());
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
        // 执行方法
        return ReflectionUtils.invokeMethod(method, bean, params);
    }
}

@Data
class DispatchInfoDto {
    // 请求目标beanid
    private String componentName;
    // 请求目标方法名
    private String methodName;
    // 请求目标方法参数
    private Object[] params;

}