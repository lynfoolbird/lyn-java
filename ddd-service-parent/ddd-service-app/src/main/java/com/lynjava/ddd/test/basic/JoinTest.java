package com.lynjava.ddd.test.basic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JoinTest {
    private static volatile int i = 1;

    private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) throws Exception {
//        for (int i=0; i<10; i++) {
            ses.scheduleAtFixedRate(() ->{
                System.out.println(Thread.currentThread().getName() + " begion");
            }, 0, 10, TimeUnit.SECONDS);
//        }
//        Thread before = null;
//        for (int i=1; i<101; i++) {
//            String threadName = "Thread-"+i;
//            Thread t = new Thread (new Dominuo(before), threadName);
//            t.start();
//            before = t;
//        }
//        final Object monitor = new Object();
//        Runnable runnable = new Runnable() {
//            public void run() {
//                synchronized (monitor) {
//                    for (;i<100;) {
//                        System.out.println(Thread.currentThread().getName() + ":" + i++);
//                        try {
//                            monitor.notifyAll();
//                            monitor.wait();
//                        } catch (Exception e) {
//                        }
//                    }
//                   monitor.notifyAll();
//                }
//            }
//        };
//        new Thread(runnable, "odd").start();
//        new Thread(runnable, "even").start();
//        Object monitor = new Object();
//        new Thread(new OddEven(true, monitor)).start();
//        new Thread(new OddEven(false, monitor)).start();
    }
}

class OddEven implements Runnable {

    private boolean printOdd;

    private Object monitor;

    public OddEven(boolean printOdd, Object monitor) {
        this.printOdd = printOdd;
        this.monitor = monitor;
    }

    public void run() {
        synchronized (monitor) {
            for (int i=1; i<=100; i++) {
                if (printOdd && i % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
                if (!printOdd && i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
                try {
                    monitor.notifyAll();
                    monitor.wait();
                } catch (Exception e) {
                }
            }
            monitor.notifyAll();
        }
        System.out.println(Thread.currentThread().getName()+" end");
    }
}

class Dominuo implements Runnable {
    private Thread beforeThread;

    public Dominuo(Thread thread) {
        this.beforeThread = thread;
    }
    public void run() {
        String ss = Thread.currentThread().getName() + ":dao";
        if (null == beforeThread) {
            System.out.println(ss);
            return;
        }
        try {
            beforeThread.join();
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
