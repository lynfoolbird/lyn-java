package com.lynjava.ddd.test.architecture.anticorruption.utils;

import java.util.Map;

public class HttpClientUtil {

   public static String doGet(String url){
        return "doGet";
    }

    public static String doPut(){
        return "doPut";
    }

    public static String doPost(String url, Map<String, Object> headers, String body){
        System.out.println("requestUrl:" + url);
        System.out.println("headers:" + headers);
        System.out.println("body:"+body);
        return "doPost end result.";
    }
   public static String doPatch(){
        return "doPatch";
    }
    public static String doDelete(){
        return "doDelete";
    }
}
