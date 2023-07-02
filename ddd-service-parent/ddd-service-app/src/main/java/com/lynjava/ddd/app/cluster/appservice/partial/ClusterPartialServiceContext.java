package com.lynjava.ddd.app.cluster.appservice.partial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterPartialServiceContext {

    private static Map<String, IClusterPartialService> clusterPartialServiceMap = new ConcurrentHashMap<>();

    public static IClusterPartialService getByType(String configType) {
        return clusterPartialServiceMap.get(configType);
    }

    public static void registerClusterPartialService(String configType, IClusterPartialService service) {
        clusterPartialServiceMap.put(configType, service);
    }
}
