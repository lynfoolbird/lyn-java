package com.lynjava.ddd.scheduler;//package com.lyn.mvnprj.component;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by li
 */
public abstract class AbstractRunnableTask {
//    @Autowired
//    private RedisClusterService redisClusterService;

    private static final int LOCKED_TIMEOUT = 20;
    private static final int ACQUIRE_TIME = 2000;

    @PostConstruct
    public void init(){
        if (isOpen()) {
            new Thread(new Task()).start();
        }
    }

    private class Task implements Runnable {
        public void run() {
            if (isRunForever()) {
                while (true) {
                    executeRunnable();
                    sleep();
                }
            } else {
                executeRunnable();
            }
        }

        private void sleep() {
            long speed = processSpeed();
            if (speed<=0) {
                return;
            }
            try {
                TimeUnit.SECONDS.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void executeRunnable() {
        String lock = null;
        if (isUseLock()) {
            lock = getLock();
            if (StringUtils.isBlank(lock)) {
                return;
            }
        }
        try {
            processBizService();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseLock(lock);
        }

    }

    protected abstract boolean isOpen();
    protected boolean isRunForever() {
        return true;
    }
    protected boolean isUseLock(){
        return false;
    }
    protected void releaseLock(String lock) {
        if (StringUtils.isNotBlank(lock)) {
//            redisClusterService.releaseLock(getLockKey(), lock);
        }
    }
    protected String getLockKey(){
        return "LIVE:LOCK:" + this.getClass().getSimpleName();
    }
    protected String getLock() {
//        String lock = redisClusterService.acquireLockWithTimeOut(getLockKey(), ACQUIRE_TIME, LOCKED_TIMEOUT);
        return "";
    }
    protected abstract void processBizService()throws Exception;

    protected long processSpeed() {
        return 1L;
    }
}