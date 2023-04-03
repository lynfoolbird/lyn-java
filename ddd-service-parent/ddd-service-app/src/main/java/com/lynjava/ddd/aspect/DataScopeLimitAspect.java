package com.lynjava.ddd.aspect;

import com.lynjava.ddd.common.annotation.DataScopeLimit;
import com.lynjava.ddd.common.utils.SpELParserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

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
public class DataScopeLimitAspect {

    @Before(value = "@annotation(com.lynjava.ddd.common.annotation.DataScopeLimit)")
    public void before(JoinPoint joinPoint) {
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
        // 通过SPEL获取接口入参
        StandardEvaluationContext context = buildContext(joinPoint);
        Object resourceIdValue = getSpelValue(resourceIdSpel, context);
        System.out.println("resourceIdValue is " + resourceIdValue);

        // 通过SPEL修改接口入参  --改不了入参？
        setSpelValue(resourceIdSpel, "456", context);
        System.out.println("===========");
    }

    private StandardEvaluationContext buildContext(JoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext(joinPoint.getArgs());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        if (args==null || args.length==0) {
            return context;
        }
        for (int i=0; i<args.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return context;
    }

    private Object getSpelValue(String key, StandardEvaluationContext context) {
        if (Objects.isNull(key)) {
            return null;
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        return expression.getValue(context);
    }

    private void setSpelValue(String key, Object newValue, StandardEvaluationContext context) {
        if (Objects.isNull(key)) {
            return ;
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        expression.setValue(context, newValue);
    }
}
