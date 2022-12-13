package com.lynjava.ddd.test.spring.custom;

public class SpringLoader {
    public static void main(String[] args) {
        LynApplicationContext context = new LynApplicationContext(LynConfig.class);
        UserService userService1 = (UserService) context.getBean("userService");
        UserService userService2 = (UserService) context.getBean("userService");
        System.out.println(userService1);
        System.out.println(userService2);
    }
}
