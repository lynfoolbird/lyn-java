package com.lynjava.ddd.aspect;

import com.lynjava.ddd.common.annotation.DistributeLockAnnotation;
import com.lynjava.ddd.common.consts.RedisLockTypeEnum;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DistributeLockAspect
 * @author li
 */
@Aspect
@Component
public class DistributeLockAspect {

    @Pointcut("@annotation(com.lynjava.ddd.common.annotation.DistributeLockAnnotation)")
    public void distributeLock() {
    }

    @Around(value = "distributeLock()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 代理对象
        Object proxyObj = pjp.getTarget();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 解析参数
        Method method = methodSignature.getMethod();
        DistributeLockAnnotation annotation = method.getAnnotation(DistributeLockAnnotation.class);
        RedisLockTypeEnum typeEnum = annotation.typeEnum();
        Object[] params = pjp.getArgs();
        String ukString = params[annotation.lockFiled()].toString();
        // 省略很多参数校验和判空
        String businessKey = typeEnum.getUniqueKey(ukString);
        String uniqueValue = UUID.randomUUID().toString();
        // 加锁
        Object result = null;
        try {
            // 调用加锁逻辑
            System.out.println("call distribute lock.");
            Thread currentThread = Thread.currentThread();
            // 将本次 Task 信息加入「延时」队列中
            holderList.add(new RedisLockDefinitionHolder(businessKey, annotation.lockTimeout(), System.currentTimeMillis(),
                    currentThread, annotation.tryCount()));
            // 执行业务操作
            result = pjp.proceed();
            // 线程被中断，抛出异常，中断此次请求
            if (currentThread.isInterrupted()) {
                throw new InterruptedException("You had been interrupted =-=");
            }
        } catch (InterruptedException e ) {
            throw new Exception("Interrupt exception, please send request again");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 请求结束后，强制删掉 key，释放锁
            System.out.println("call distribute unlock.");
        }
        return result;
    }

    /**
     * 扫描的任务队列
     */
    private final static ConcurrentLinkedQueue<RedisLockDefinitionHolder> holderList = new ConcurrentLinkedQueue<>();
    /**
     * 线程池，维护keyAliveTime
     */
    private static final ScheduledExecutorService SCHEDULER = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder()
                    .namingPattern("redisLock-schedule-pool")
                    .daemon(true)
                    .build());
    {
        // 两秒执行一次「续时」操作
        SCHEDULER.scheduleAtFixedRate(() -> {
            Iterator<RedisLockDefinitionHolder> iterator = holderList.iterator();
            while (iterator.hasNext()) {
                RedisLockDefinitionHolder holder = iterator.next();
                // 判断 key 是否还有效，无效的话进行移除  holder.getBusinessKey()
                // 超时重试次数，超过时给线程设定中断
                if (holder.getCurrentCount() > holder.getTryCount()) {
                    holder.getCurrentTread().interrupt();
                    iterator.remove();
                    continue;
                }
                // 判断是否进入最后三分之一时间，若是则续期
                long curTime = System.currentTimeMillis();
                boolean shouldExtend = (holder.getLastModifyTime() + holder.getModifyPeriod()) <= curTime;
                if (shouldExtend) {
                    holder.setLastModifyTime(curTime);
                    // 重置key过期时间
                    holder.setCurrentCount(holder.getCurrentCount() + 1);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
