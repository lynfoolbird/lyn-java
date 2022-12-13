package com.lynjava.ddd.scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 启动后执行逻辑02
 */
@Component
@Order(1)
public class TestCommandLineRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("TestCommandLineRunner run args:" + args);
        System.out.println("TestCommandLineRunner run execute...");
    }

    @PostConstruct
    public void init() {
        System.out.println("TestCommandLineRunner init execute...");
    }

    @PreDestroy
    public void destry() {
        System.out.println("TestCommandLineRunner destroy execute...");
    }
}
