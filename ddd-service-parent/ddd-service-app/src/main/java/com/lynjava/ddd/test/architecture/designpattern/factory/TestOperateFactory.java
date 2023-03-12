package com.lynjava.ddd.test.architecture.designpattern.factory;

import org.springframework.cglib.core.ReflectUtils;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class TestOperateFactory {
    private static Map<String, ITestOperateService> map = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("okkk");
    }

    static {
        String pkgname = TestOperateFactory.class.getPackage().getName();
        String packageDirName = pkgname.replace('.', '/');

        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                File file = new File(url.getFile());
                File[] files = file.listFiles();
                for (File ff : files) {
                    String clazzName = ff.getName().substring(0, ff.getName().length() - 6);
                    String clzFullName = pkgname + "." + clazzName;
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(clzFullName);
                    if (ITestOperateService.class.isAssignableFrom(clazz)
                    && !clazz.isInterface()) {
                        ITestOperateService instance = (ITestOperateService) ReflectUtils.newInstance(clazz);
                        map.put(instance.opearteType(), instance);
                    }

                }
            }
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ITestOperateService getByType(String oprType) {
        return map.get(oprType);
    }

}
