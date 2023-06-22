package com.lynjava.ddd.test.architecture.designpattern.strategy;

import org.springframework.cglib.core.ReflectUtils;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式：JDK反射加载
 */
public class TestStrategyContext {
    private static Map<String, IOperateService> map = new HashMap<>();

    public static void main(String[] args) {
        TestStrategyContext.getByType("StrategyB").doSomething();
        System.out.println("okkk");
    }

    // 通过JDK工具类
    static {
        String pkgName = TestStrategyContext.class.getPackage().getName();
        String packageDirName = pkgName.replace('.', '/');

        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                File file = new File(url.getFile());
                File[] files = file.listFiles();
                for (File ff : files) {
                    String clazzName = ff.getName().substring(0, ff.getName().length() - 6);
                    String clzFullName = pkgName + "." + clazzName;
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(clzFullName);
                    if (IOperateService.class.isAssignableFrom(clazz)
                        && !clazz.isInterface()) {
                        IOperateService instance = (IOperateService) ReflectUtils.newInstance(clazz);
                        map.put(instance.getOperateType(), instance);
                    }
                }
            }
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IOperateService getByType(String oprType) {
        return map.get(oprType);
    }

}
