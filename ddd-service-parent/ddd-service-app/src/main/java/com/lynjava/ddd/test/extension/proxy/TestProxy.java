package com.lynjava.ddd.test.extension.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestProxy<T> implements InvocationHandler {
    private Session session;
    private String methodName;
    /**
     * 若是代理接口，则target字段为Class对象；
     * 若是代理接口的实现类，则target字段值为实现类实例
     */
    private T target;

    public TestProxy(T target, Session session, String methodName) {
        this.target = target;
        this.session = session;
        this.methodName = methodName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre handler...");
        Object obj = null;
        String fullMethodName = method.getDeclaringClass().getName()+"."+method.getName();
        System.out.println("method's fullName=" + fullMethodName);
        System.out.println("method's params=" + Arrays.toString(args));
        System.out.println("method's returnType=" + method.getReturnType());
        if (target instanceof Class) {
            if ("SELECT".equals(this.methodName)) {
                obj = this.session.select();
            }
        } else {
            obj = method.invoke(target, args);
        }
        System.out.println(obj);
        System.out.println("post handler");
        return obj;
    }
}
