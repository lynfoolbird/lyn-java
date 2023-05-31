package com.lynjava.rpc;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class LynRpcProcessor implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}