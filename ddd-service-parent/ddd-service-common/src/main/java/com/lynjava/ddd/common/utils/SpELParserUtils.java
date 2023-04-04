package com.lynjava.ddd.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * SpELParserUtils
 *
 * @author li
 */
@Slf4j
public class SpELParserUtils {
    private static final String EXPRESSION_PREFIX = "#{";

    private static final String EXPRESSION_SUFFIX = "}";

    /**
     * 表达式解析器
     */
    private static final ExpressionParser parser = new SpelExpressionParser();

    /**
     *  参数名解析器，用于获取参数名
     */
    private static final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private SpELParserUtils(){}

    /**
     * 解析spel表达式，带默认值
     *
     * @param method 方法
     * @param args 参数值
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    public static <T> T parse(Method method, Object[] args, String spelExpression, Class<T> clz, T defaultResult) {
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        //设置上下文变量
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        T result = getResult(context, spelExpression, clz);
        if(Objects.isNull(result)){
            return defaultResult;
        }
        return result;
    }

    /**
     * 解析spel表达式，无默认值
     *
     * @param method  方法
     * @param args 参数值
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @return 执行spel表达式后的结果
     */
    public static <T> T parse(Method method, Object[] args, String spelExpression, Class<T> clz) {
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        //设置上下文变量
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        return getResult(context, spelExpression, clz);
    }

    /**
     * 解析spel表达式
     *
     * @param joinPoint  joinPoint
     * @param spelExpression  表达式
     * @return 执行spel表达式后的结果
     */
    public static Object parse(JoinPoint joinPoint, String spelExpression) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] params = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        Object[] args = joinPoint.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        //设置上下文变量
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        return getResult(context, spelExpression, Object.class);
    }

    /**
     * 解析spel表达式
     *
     * @param joinPoint joinPoint
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @return 执行spel表达式后的结果
     */
    public static <T> T parse(JoinPoint joinPoint, String spelExpression, Class<T> clz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (Objects.isNull(paramNames) || paramNames.length==0) {
            return null;
        }
        Object[] args = joinPoint.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return getResult(context, spelExpression, clz);
    }

    /**
     * 解析spel表达式，带默认值
     *
     * @param param 参数名
     * @param paramValue 参数值
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    public static <T> T parse(String param, Object paramValue, String spelExpression, Class<T> clz, T defaultResult) {
        EvaluationContext context = new StandardEvaluationContext();
        //设置上下文变量
        context.setVariable(param, paramValue);
        T result = getResult(context, spelExpression, clz);
        if(Objects.isNull(result)){
            return defaultResult;
        }
        return result;
    }

    /**
     * 解析spel表达式，不带默认值
     *
     * @param param  参数名
     * @param paramValue 参数值
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @return 执行spel表达式后的结果
     */
    public static <T> T parse(String param, Object paramValue, String spelExpression, Class<T> clz) {
        EvaluationContext context = new StandardEvaluationContext();
        //设置上下文变量
        context.setVariable(param, paramValue);
        return getResult(context, spelExpression,clz);
    }

    /**
     * 获取spel表达式后的结果
     *
     * @param context 解析器上下文接口
     * @param spelExpression  表达式
     * @param clz  返回结果的类型
     * @return 执行spel表达式后的结果
     */
    private static <T> T getResult(EvaluationContext context, String spelExpression, Class<T> clz) {
        try {
            // 如果表达式是一个#{}表达式，需要为解析传入模板解析器上下文
            boolean isJingExpression = spelExpression.startsWith(EXPRESSION_PREFIX) && spelExpression.endsWith(EXPRESSION_SUFFIX);
            Expression expression = isJingExpression
                    ? parser.parseExpression(spelExpression, new TemplateParserContext())
                    : parser.parseExpression(spelExpression);
            return expression.getValue(context, clz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
