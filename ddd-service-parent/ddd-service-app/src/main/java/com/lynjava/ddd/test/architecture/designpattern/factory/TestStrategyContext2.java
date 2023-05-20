package com.lynjava.ddd.test.architecture.designpattern.factory;

import com.google.common.reflect.ClassPath;
import java.util.HashMap;
import java.util.Map;

public class TestStrategyContext2 {
    private static Map<String, ITestOperateService> map = new HashMap<>();

    public static void main(String[] args) {
        TestStrategyContext2.getByType("OPRB").operate();
        System.out.println("okkk");
    }

    // 通过guava反射包工具
    static {
        try {
            String pkgName = TestStrategyContext.class.getPackage().getName();
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(pkgName)) {
                Class clazz = Class.forName(classInfo.getName());
                if (ITestOperateService.class.isAssignableFrom(clazz)
                        && !clazz.isInterface()) {
                    ITestOperateService instance = (ITestOperateService) clazz.asSubclass(ITestOperateService.class)
                            .newInstance();
                    map.put(instance.opearteType(), instance);
                }
                System.out.println(classInfo);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static ITestOperateService getByType(String oprType) {
        return map.get(oprType);
    }

}
