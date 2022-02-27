package com.lynjava.ddd.test.requestmerge;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 请求合并
 */
public class RequestMergeDemo {
    // 周期处理请求线程池
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // 请求任务队列，周期批量处理请求
    private final static LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    static {
        scheduler.scheduleAtFixedRate(() -> {
            int size = queue.size();
            if (size == 0) {
                return;
            }
            // 收集请求，批量查询
            List<String> orderIds = queue.stream().map(Request::getOrderId).collect(Collectors.toList());
            Map<String, HashMap<String, Object>> response = new QueryServiceRemoteCall().queryCommodityByCodeBatch(orderIds);
            for (Request request : queue.stream().collect(Collectors.toList())) {
                Map<String,Object> result = response.get(request.orderId);
                request.completableFuture.complete(result);
            }

        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i=0; i<100; i++) {
            final String code = "code"+i;
            Thread thread = new Thread(() -> {
                try {
                    Map<String, Object> map = queryCommodity("000" + code);
                    System.out.println(Thread.currentThread().getName() + "的查询结果是:" + map);
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "出现异常:" + e.getMessage());
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
            thread.setName("price-thread-" + code);
            thread.start();
        }
        countDownLatch.await();
        System.out.println("end=====");
    }

    private static Map<String,Object> queryCommodity(String orderId) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.orderId = orderId;
        CompletableFuture<Map<String,Object>> future = new CompletableFuture<>();
        request.completableFuture =  future;
        //将对象传入队列
        queue.add(request);
        //如果这时候没完成赋值,那么就会阻塞,直到能够拿到值
        return future.get();
    }
}

class Request {
    // 请求id
    String orderId;
    // 响应结果
    CompletableFuture<Map<String, Object>> completableFuture;

    public String getOrderId() {
        return orderId;
    }
}

class QueryServiceRemoteCall {
    public Map<String, HashMap<String, Object>> queryCommodityByCodeBatch(List<String> codes) {
        Map<String,HashMap<String, Object>> result = new HashMap();
        for (String code : codes) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("commodityId", new Random().nextInt(999999999));
            hashMap.put("code", code);
            hashMap.put("phone", "huawei");
            hashMap.put("isOk", "true");
            hashMap.put("price","4000");
            result.put(code,hashMap);
        }
        return result;
    }
}