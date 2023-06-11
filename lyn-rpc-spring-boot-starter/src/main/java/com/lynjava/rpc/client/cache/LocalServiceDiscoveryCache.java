package com.lynjava.rpc.client.cache;

import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 服务注册表本地缓存
 * @author li
 */
public class LocalServiceDiscoveryCache {
    /**
     * key: serviceName
     */
    private static final Map<String, List<ServiceInfo>> SERVER_MAP = new ConcurrentHashMap<>();

    public static void put(String serviceKey, List<ServiceInfo> serviceList) {
        SERVER_MAP.put(serviceKey, serviceList);
    }

    /**
     * 去除指定的值
     * @param serviceKey
     * @param service
     */
    public static void remove(String serviceKey, ServiceInfo service) {
        SERVER_MAP.computeIfPresent(serviceKey, (key, value) ->
                value.stream().filter(o -> !o.toString().equals(service.toString())).collect(Collectors.toList())
        );
    }

    public static void removeAll(String serviceKey) {
        SERVER_MAP.remove(serviceKey);
    }


    public static boolean isEmpty(String serviceKey) {
        return SERVER_MAP.get(serviceKey) == null || SERVER_MAP.get(serviceKey).size() == 0;
    }

    public static List<ServiceInfo> get(String serviceKey) {
        return SERVER_MAP.get(serviceKey);
    }
}
