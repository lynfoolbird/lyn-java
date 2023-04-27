package com.lynjava.ddd.test.java.jdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.BiFunction;

public class Jdk8FunctionalInterfaceDemo {
    public static void main(String[] args) {
        String prefix = "_clu_";
        print(
                (sentinelCluster, sentinelHost) ->
             sentinelCluster.getId() + prefix + sentinelHost.getHostName()
        );

        print2(sentinelHost -> {
            System.out.println("hostname=" + sentinelHost.getHostName());
            SentinelCluster sc = new SentinelCluster("02", prefix +"cluster02");
            return sc;
        });
    }


    public static void print(BiFunction<SentinelCluster, SentinelHost, String> biFunction){
        SentinelCluster sc = new SentinelCluster("01", "cluster01");
        SentinelHost sh = new SentinelHost("host03", 8001, "01");
        String res = biFunction.apply(sc, sh);
        System.out.println(res);
    }

    public static void print2(FIdemo fIdemo) {
        SentinelHost sh = new SentinelHost("host04", 8001, "01");
        System.out.println(fIdemo.process(sh));
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SentinelCluster {
        private String id;
        private String name;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SentinelHost {
        private String hostName;
        private Integer port;
        private String sentinelClusterId;
    }

    @FunctionalInterface
    public interface FIdemo {
        SentinelCluster process(SentinelHost sentinelHost);
    }
}

