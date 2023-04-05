package com.lynjava.ddd.test.java.jdk;

public class InterruptTest {
    public static void main(String[] args) throws Exception {
        Task task = new Task();
        Thread t = new Thread(task);
        t.start(); //启动任务线程
        Thread.sleep(1000); //主线程休眠1秒
        t.interrupt();//三秒后中断任务线程

    }
    public static class Task implements Runnable {
        public void run() {
            while(!Thread.interrupted()){ //检查线程中断状态
                System.out.println("I'm running...");
            }
            System.out.println("I'm stopped.");
        }
    }
}
