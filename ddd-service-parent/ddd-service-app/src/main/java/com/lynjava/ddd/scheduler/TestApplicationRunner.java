package com.lynjava.ddd.scheduler;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动后执行逻辑01
 */
@Component
public class TestApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("TestApplicationRunner run args:" + args);
        System.out.println("TestApplicationRunner run execute...");
    }
}
