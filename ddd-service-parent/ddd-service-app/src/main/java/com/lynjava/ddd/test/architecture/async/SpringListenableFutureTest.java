package com.lynjava.ddd.test.architecture.async;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class SpringListenableFutureTest {
    public static void main(String[] args) {
        ListenableFutureTask task = new ListenableFutureTask(() -> {
            Thread.sleep(5000);
            return "success";
        });

        task.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("exception: " + ex.getMessage());
            }

            @Override
            public void onSuccess(Object result) {
                System.out.println("result is: " + result);
            }
        });

        Executors.newSingleThreadExecutor().submit(task);
        System.out.println("end");
    }
}
