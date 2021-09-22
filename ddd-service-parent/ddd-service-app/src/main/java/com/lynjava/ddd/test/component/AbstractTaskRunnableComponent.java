package com.lynjava.ddd.test.component;//package com.lyn.mvnprj.component;
//
//import com.lyn.service.redis.RedisClusterService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by liyanan on 2018/5/26.
// */
//public abstract class AbstractTaskRunnableComponent {
//
//    @Autowired
//    private RedisClusterService redisClusterService;
//
//    private static final int LOCKED_TIMEOUT = 20;
//    private static final int ACQUIRE_TIME = 2000;
//
//    @PostConstruct
//    public void init(){
//        if (isOpen()) {
//            new Thread(new Task()).start();
//        }
//    }
//
//    protected abstract boolean isOpen();
//
//    private class Task implements Runnable {
//
//        public void run() {
//            if (isRunForover()) {
//                while (true) {
//                    executeRunnable();
//                    sleep();
//                }
//            } else {
//                executeRunnable();
//            }
//
//        }
//        private void sleep(){
//            long speed = processSpeed();
//            if (speed<=0) {
//                return;
//            }
//            try {
//                TimeUnit.SECONDS.sleep(speed);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    protected void executeRunnable(){
//        String lock = null;
//        if (isUsedLock()) {
//            lock = getLock();
//            if (StringUtils.isBlank(lock)) {
//                return;
//            }
//        }
//        try {
//            processBizService();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (StringUtils.isNotBlank(lock)) {
//                releaseLock(lock);
//            }
//        }
//
//    }
//    protected boolean isRunForover(){
//        return true;
//    }
//    protected boolean isUsedLock(){
//        return false;
//    }
//    protected void releaseLock(String lock) {
//        redisClusterService.releaseLock(getLockKey(), lock);
//    }
//    protected String getLockKey(){
//        return "LIVE:LOCK:" + this.getClass().getSimpleName();
//    }
//    protected String getLock(){
//        return redisClusterService.acquireLockWithTimeOut(getLockKey(), ACQUIRE_TIME, LOCKED_TIMEOUT);
//    }
//    protected abstract void processBizService()throws Exception;
//    protected long processSpeed() {
//        return 1L;
//    }
//}
