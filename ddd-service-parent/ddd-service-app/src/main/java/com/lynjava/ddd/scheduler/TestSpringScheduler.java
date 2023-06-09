package com.lynjava.ddd.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring定时任务
 */
@Component
@EnableScheduling
public class TestSpringScheduler {

    @Scheduled(fixedRate = 30000)
    public void testScheduler() {
        System.out.println("testScheduler...");
    }
}
