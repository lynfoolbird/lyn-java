package com.lynjava.ddd.common.config;

import cn.hutool.core.thread.RejectPolicy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@ConfigurationProperties(prefix = "thread-pool")
@Data
public class ThreadPoolConfig {
// TODO 待解决：多模块工程，common模块无法读取provide下的配置文件
    private Integer corePoolSize=1;

    private Integer maxPoolSize = 2;

    private Integer blockQueueSize = 1;

    private Integer keepAliveTime = 10;

    // 线程工厂
    private ThreadFactory buildCommonThreadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "commonThreadPool_" + atomicInteger.getAndIncrement() + "_|_");
            }
        };
        return threadFactory;
    }

    @Bean
    public ExecutorService executorService() {
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(blockQueueSize),
                buildCommonThreadFactory(),
                RejectPolicy.CALLER_RUNS.getValue()); // 用调用者线程执行
        return executorService;
    }
}
