package com.lynjava.ddd.aspect;

import com.lynjava.ddd.common.annotation.DataScopeLimit;
import com.lynjava.ddd.common.utils.SpELParserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 横向越权校验
 *
 * 资源id用spel表达式取值，支持list形式;
 * 根据资源类型查找资源所属项目;
 * 从用户登录请求上下文中获取用户角色权限等，对比鉴权
 *
 * @author li
 */
@Aspect
@Component
@Order(1)
public class DataScopeLimitAspect {

    @Before(value = "@annotation(com.lynjava.ddd.common.annotation.DataScopeLimit)")
    public void before(JoinPoint joinPoint) throws Throwable {
        // 代理对象
        Object proxyObj = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 解析参数
        Method method = methodSignature.getMethod();
        DataScopeLimit dataScopeLimit = method.getAnnotation(DataScopeLimit.class);
        Object[] params = joinPoint.getArgs();
        String resourceIdSpel = dataScopeLimit.resourceIdSpel();
        String resourceType = dataScopeLimit.resourceType();
        String[] rights = dataScopeLimit.rights();

        Object resourceId = SpELParserUtils.parse(joinPoint, resourceIdSpel);
        if (resourceId instanceof String) {
            String[] resourceIdArray = ((String) resourceId).split(";");
            System.out.println("string = " + resourceId);
        } else if (resourceId instanceof List) {
            System.out.println("list = " + resourceId);
        } else {

        }
        System.out.println("===========");
    }
}
