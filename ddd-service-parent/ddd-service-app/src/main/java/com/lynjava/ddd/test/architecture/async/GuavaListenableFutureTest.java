package com.lynjava.ddd.test.architecture.async;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * spring: ListenableFutureTask
 *
 */
public class GuavaListenableFutureTest {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ListeningExecutorService pool = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(5));
        Task task1 = new Task();
        task1.args = "task1";
        Task task2 = new Task();
        task2.args = "task2";
        ListenableFuture<String> future = pool.submit(task1);
        ListenableFuture<String> future2 = pool.submit(task2);

        future2.addListener(() -> {
            System.out.println("hello world");
        }, pool);

        FutureCallback<String> futureCallback = new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String s) {
                System.out.println("result is: " + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        };
        Futures.addCallback(future, futureCallback, pool);
    }

    public static class Task implements Callable<String> {
        String args;

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            System.out.println("任务: " + args);
            throw new Exception("exc");
//            return "dong";
        }
    }
}
