package com.lynjava.ddd.domain.cluster.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncService {

    @Async("taskExector")
    public Future<String> print() {
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println("async print start");
            Thread.sleep(5000);
            System.out.println("async print end");
            return new AsyncResult<String>("helloPring");
//            throw new Exception("helloexception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AsyncResult<String>("null");
    }
}
