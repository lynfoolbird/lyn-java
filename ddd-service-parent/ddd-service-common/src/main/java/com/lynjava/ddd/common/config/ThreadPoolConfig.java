package com.lynjava.ddd.common.config;

import cn.hutool.core.thread.RejectPolicy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolConfig {
    private Integer corePoolSize;

    private Integer maxPoolSize = 20;

    private Integer blockQueueSize = 10;

    private Integer keepAliveTime = 100;

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
        System.out.println(this.corePoolSize);
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(blockQueueSize),
                buildCommonThreadFactory(),
                RejectPolicy.CALLER_RUNS.getValue()); // 用调用者线程执行
        return executorService;
    }

    @Bean("asyncTaskExector")
    public ThreadPoolTaskExecutor asyncTaskExector() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setQueueCapacity(blockQueueSize);
        threadPoolTaskExecutor.setThreadNamePrefix("asyncTask-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(RejectPolicy.CALLER_RUNS.getValue());
        return threadPoolTaskExecutor;
    }
}
