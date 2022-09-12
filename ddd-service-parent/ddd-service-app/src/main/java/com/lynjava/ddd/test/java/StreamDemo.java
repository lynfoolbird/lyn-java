package com.lynjava.ddd.test.java;

import com.lynjava.ddd.test.java.model.SentinelHost;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        SentinelHost sh01 = new SentinelHost("host1", 8001, "01");
        SentinelHost sh02 = new SentinelHost("host2", 8002,"01");
        List<SentinelHost> list = Arrays.asList(sh01, sh02);
        List<String> hostPorts = list.stream()
                .map(sentinelHost -> sentinelHost.getHostName() + ":" + sentinelHost.getPort())
                .collect(Collectors.toList());
    }


}
