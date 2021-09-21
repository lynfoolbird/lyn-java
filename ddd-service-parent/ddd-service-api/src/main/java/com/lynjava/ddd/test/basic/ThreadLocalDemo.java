package com.lynjava.ddd.test.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {
    private static ThreadLocal<String> localVar = new ThreadLocal<String>();

    private static ExecutorService threadpool = Executors.newFixedThreadPool(2);

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :" + localVar.get());
        //清除本地内存中的本地变量
        localVar.remove();
    }
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    ThreadLocalDemo.localVar.set(Thread.currentThread().getName());
//                    print("A");
//                    //打印本地变量
//                    System.out.println("after remove : " + localVar.get());
//                }
//            };
//            new Thread(runnable).start();
            Runnable run = new RunTest(i, localVar);
            threadpool.execute(run);
        }
        threadpool.shutdown();

//        new Thread(new Runnable() {
//            public void run() {
//                ThreadLocalDemo.localVar.set("local_A");
//                print("A");
//                //打印本地变量
//                System.out.println("after remove : " + localVar.get());
//
//            }
//        }, "A").start();
    }

}

class RunTest implements Runnable {

    private int i;
    private ThreadLocal<String> threadLocal;
    public RunTest(int i, ThreadLocal<String> threadLocal) {
        this.i = i;
        this.threadLocal = threadLocal;
    }

    public void run() {
        System.out.println( Thread.currentThread().getName()+":hehe:"+threadLocal.get());
        String name = Thread.currentThread().getName() + i;
        threadLocal.set(name);
//        System.out.println(Thread.currentThread().getName() + " :" + threadLocal.get());
    }
}
