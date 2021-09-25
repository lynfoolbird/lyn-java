package com.lynjava.ddd.app.components;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("TestApplicationRunner run args:" + args);
        System.out.println("TestApplicationRunner run execute...");
    }
}
