package com.lynjava.ddd.test.architecture.designpattern.strategy;

import com.google.common.reflect.ClassPath;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式：guava反射包加载
 */
public class TestStrategyContext2 {
    private static Map<String, IOperateService> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        TestStrategyContext2.getByType("StrategyA").doSomething();
        System.out.println("okkk");
    }

    // 通过guava反射包工具
    static {
        try {
            String pkgName = TestStrategyContext.class.getPackage().getName();
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(pkgName)) {
                Class clazz = Class.forName(classInfo.getName());
                if (IOperateService.class.isAssignableFrom(clazz)
                        && !clazz.isInterface()) {
                    IOperateService instance = (IOperateService) clazz.asSubclass(IOperateService.class)
                            .newInstance();
                    map.put(instance.getOperateType(), instance);
                }
                System.out.println(classInfo);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void register(String type, IOperateService operateService) {
        map.put(type, operateService);
    }

    public static IOperateService getByType(String oprType) {
        return map.get(oprType);
    }

}
