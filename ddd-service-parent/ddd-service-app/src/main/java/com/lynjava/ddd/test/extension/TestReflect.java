package com.lynjava.ddd.test.extension;


/**
 * 反射demo
 *
 * JDK反射工具/guava反射工具/spring反射工具/apache反射工具/其他...
 * @author li
 */
public class TestReflect {
    public static void main(String[] args) throws Exception {
        // ClassLoader对象

        // Class对象
        Class clz = getTargetClass();
        // 创建实例

        // 类的注解
        // 父类、接口信息
        // Constructor对象
        // Field对象
        // Method对象
    }

    /**
     * 获取Class对象的方式
     *
     * 区别：无初始化、静态初始化、全初始化；说明：静态初始化只执行一次
     * 单个类的加载顺序:
     * 1、首先加载父类，执行父类静态块，再加载子类，执行子类静态块
     * 2、其次构造父类对象，先执行动态块，再执行构造器方法
     * 3、再是构造子类对象，先执行动态块，再执行构造器方法
     * @return Class
     * @throws Exception
     */
    private static Class getTargetClass() throws Exception {
        String str = "com.lynjava.ddd.test.extension.Son";
        // 直接访问：静态初始化
//        System.out.println(Son.getCountry());
        System.out.println("---------------------");
        // 1、不执行初始化代码
        Class clz11 = Son.class;
        Class clz12 = TestReflect.class.getClassLoader().loadClass(str);
        Class clz13 = Thread.currentThread().getContextClassLoader().loadClass(str);
        System.out.println("---------------------");
        // 2、静态初始化
        Class clz21 = Class.forName(str);
        System.out.println("---------------------");
        // 3、全初始化
        Class clz31 = new Son().getClass();
        return clz21;
    }
}
