package com.lynjava.ddd.test.extension.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author li
 * @param <T>
 */
public class TestInvocationHandler<T> implements InvocationHandler {
    private Session session;
    /**
     * 若是代理接口，则target字段为Class对象；
     * 若是代理接口的实现类，则target字段值为实现类实例
     */
    private final T target;

    public TestInvocationHandler(T target, Session session) {
        this.target = target;
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy pre handler...");
        Object result = null;
        String fullMethodName = method.getDeclaringClass().getName()+"."+method.getName();
        System.out.println("method's fullName=" + fullMethodName);
        System.out.println("method's params=" + Arrays.toString(args));
        System.out.println("method's returnType=" + method.getReturnType());
        if (target instanceof Class) {
            switch (method.getName()) {
                case "select":
                    result = this.session.select((String) args[0]);
                    break;
                case "insert" :
                    result = this.session.insert((String) args[0]);
                    break;
                default:
                    return "not found method.";
            }
        } else {
            result = method.invoke(target, args);
        }
        System.out.println(result);
        System.out.println("proxy post handler...");
        return result;
    }
}
