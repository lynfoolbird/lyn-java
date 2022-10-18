package com.lynjava.ddd.test.request;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestDispatch {
    private static Map<String, ConcurrentLinkedQueue<DataDto>> map = new ConcurrentHashMap<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        consume();
    }

    public static void consume() {
        List<DataDto> dataDtoList = new ArrayList<>();
        for (int i=0; i<100; i++) {
            DataDto dataDto = new DataDto();
            dataDto.setId(i + "");
            dataDto.setAppId((Math.round(Math.random()*20))+"");
            dataDtoList.add(dataDto);
        }
        for (DataDto dataDto : dataDtoList) {
            synchronized (RequestDispatch.class) {
                String key = String.valueOf(dataDto.getAppId().hashCode() % 10);
                if (!map.containsKey(key)) {
                    ConcurrentLinkedQueue<DataDto> queue = new ConcurrentLinkedQueue<>();
                    queue.add(dataDto);
                    map.put(key, queue);
                    executorService.execute(() -> {
                        while (!map.get(key).isEmpty()) {
                            DataDto dto = map.get(key).poll();
                            System.out.println(JSON.toJSONString(dto));
                        }
                        map.remove(key);
                    });
                } else {
                    map.get(key).offer(dataDto);
                }
            }
        }
    }

    @Data
    public static class DataDto {
        private String id;
        private String appId;
    }
}
