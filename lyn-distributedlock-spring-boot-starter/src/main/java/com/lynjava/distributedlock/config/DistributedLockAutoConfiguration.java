package com.lynjava.distributedlock.config;

import com.lynjava.distributedlock.api.IDistributedLock;
import com.lynjava.distributedlock.mapper.DbDistributedLockDao;
import com.lynjava.distributedlock.service.DbDistributedLockImpl;
import com.lynjava.distributedlock.task.LockRenewalTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Import({DbDistributedLockProperties.class})
public class DistributedLockAutoConfiguration {
    @Bean
    public LockRenewalTask lockRenewalTask(DbDistributedLockDao dbDistributedLockDao, DbDistributedLockProperties dbDistributedLockProperties) {
        return new LockRenewalTask(dbDistributedLockDao, dbDistributedLockProperties);
    }

    // 自动续期定时任务
    @Bean
    public ScheduledExecutorService renewalTaskScheduledService(LockRenewalTask lockRenewalTask, DbDistributedLockProperties dbDistributedLockProperties) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadPoolExecutor.AbortPolicy());
        scheduledExecutorService.scheduleAtFixedRate(lockRenewalTask, 0L, dbDistributedLockProperties.getExclusive().getRenewalInterval(), TimeUnit.SECONDS);
        return scheduledExecutorService;
    }
    @Bean
    public DbDistributedLockDao dbDistributedLockDao() {
        return new DbDistributedLockDao();
    }

    @Bean
    public IDistributedLock distributedLock(LockRenewalTask lockRenewalTask, DbDistributedLockDao dbDistributedLockDao, DbDistributedLockProperties dbDistributedLockProperties) {
        return new DbDistributedLockImpl(lockRenewalTask, dbDistributedLockDao, dbDistributedLockProperties);
    }
}
