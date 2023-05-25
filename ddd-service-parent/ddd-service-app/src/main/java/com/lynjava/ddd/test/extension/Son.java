package com.lynjava.ddd.test.extension;

public class Son extends Father {
    private static String country;

    private String name;

    static {
        System.out.println("static init son.");
        country = "CHINA";
    }

    {
        System.out.println("dynamic init son.");
    }
    public Son() {
        this.name = "Son";
        System.out.println("execute constructor son");
    }

    public String getName() {
        return name;
    }

    public static String getCountry() {
        return country;
    }
}

class Father {
    private static String country = "HELLO";

    private String name;

    static {
        System.out.println("static init father.");
        country = "CHINA";
    }

    {
        System.out.println("dynamic init father.");
    }
    public Father() {
        this.name = "Father";
        System.out.println("execute constructor father");
    }

    public String getName() {
        return name;
    }
}
