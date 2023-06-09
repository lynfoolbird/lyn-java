package com.lynjava.ddd.common.config;

import cn.hutool.core.thread.RejectPolicy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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

    /**
     * 资源上报线程池
     * @return
     */
    @Bean
    public ExecutorService resourceUploadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(blockQueueSize),
                buildCommonThreadFactory(),
                RejectPolicy.CALLER_RUNS.getValue());
        return executorService;
    }

    /**
     * 通用异步任务线程池
     * @return
     */
    @Bean("asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setQueueCapacity(blockQueueSize);
        threadPoolTaskExecutor.setThreadNamePrefix("asyncTask-");
        threadPoolTaskExecutor.setTaskDecorator(new ContextDecorator());
        // 用调用者线程执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(RejectPolicy.CALLER_RUNS.getValue());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    /**
     * 异步线程传递请求上下文
     */
    class ContextDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(context);
                    runnable.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }

}
