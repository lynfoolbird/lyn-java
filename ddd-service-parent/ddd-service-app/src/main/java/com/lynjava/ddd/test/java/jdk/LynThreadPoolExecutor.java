package com.lynjava.ddd.test.java.jdk;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class LynThreadPoolExecutor implements Executor {
    // 池中线程数
    private AtomicInteger ctl = new AtomicInteger(0);

    private volatile int corePoolSize;

    private volatile int maxPoolSize;

    private BlockingQueue<Runnable> workQueue = null;

    public LynThreadPoolExecutor(int corePoolSize, int maxPoolSize, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueue = workQueue;
    }
    @Override
    public void execute(Runnable command) {
        int c = ctl.get();
        if (c < corePoolSize) {
            if (!addWorker(command)) {
                reject();
            }
            return;
        }
        if (!workQueue.offer(command)) {
            if (!addWorker(command)) {
                reject();
            }
        }
    }

    private boolean addWorker(Runnable firstTask) {
        if (ctl.get() > maxPoolSize) {
            return false;
        }
        Worker worker = new Worker(firstTask);
        worker.thread.start();
        ctl.incrementAndGet();
        return true;
    }

    private void reject() {
        throw new RuntimeException("can't execute,ctl.count is " + ctl.get() + ", workQueue size is " + workQueue.size());
    }
    private class Worker implements Runnable {
        private Thread thread;

        private Runnable firstTask;

        public Worker(Runnable firstTask) {
            this.thread = new Thread(this);
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            Runnable task = firstTask;
            try {
                while (task != null || (task = getTask()) != null) {
                    task.run();
                    if (ctl.get() > maxPoolSize) {
                        break;
                    }
                    task = null;
                }
            } finally {
                ctl.decrementAndGet();
            }
        }

        private Runnable getTask() {
            for (;;) {
                try {
                    return workQueue.take();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        LynThreadPoolExecutor pool = new LynThreadPoolExecutor(2, 2,
                new ArrayBlockingQueue<Runnable>(10));
        for (int i=0; i<10; i++) {
            int taskNum = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("taskNum is " + taskNum);
            });
        }
    }
}
