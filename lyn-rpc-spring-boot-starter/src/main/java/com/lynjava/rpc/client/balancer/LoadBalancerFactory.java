package com.lynjava.rpc.client.balancer;

import com.lynjava.rpc.client.balancer.impl.RandomLoadBalancer;
import com.lynjava.rpc.client.balancer.impl.RoundRobinBalancer;
import com.lynjava.rpc.client.balancer.impl.WeightRoundBalancer;
import com.lynjava.rpc.core.consts.LoadBalancerTypeEnum;
import com.lynjava.rpc.core.exception.RpcException;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端负载均衡器工厂
 *
 * @author li
 */
public class LoadBalancerFactory {
    private final static Map<String, ILoadBalancer> BALANCER_MAP = new ConcurrentHashMap<>();

    // 内置负载均衡器
    static {
        BALANCER_MAP.put(LoadBalancerTypeEnum.RANDOM.getCode(), new RandomLoadBalancer());
        BALANCER_MAP.put(LoadBalancerTypeEnum.ROUND_ROBIN.getCode(), new RoundRobinBalancer());
        BALANCER_MAP.put(LoadBalancerTypeEnum.WEIGHT_ROUND.getCode(), new WeightRoundBalancer());
    }
    public static ILoadBalancer getLoadBalancer(String code) {
        if (StringUtils.isEmpty(code)) {
            return BALANCER_MAP.get(LoadBalancerTypeEnum.RANDOM.getCode());
        }
        if (BALANCER_MAP.containsKey(code)) {
            return BALANCER_MAP.get(code);
        }
        ServiceLoader<ILoadBalancer> loader = ServiceLoader.load(ILoadBalancer.class);
        Iterator<ILoadBalancer> iterator = loader.iterator();
        while (iterator.hasNext()) {
            ILoadBalancer balancer = iterator.next();
            BALANCER_MAP.putIfAbsent(balancer.getUniqueCode(), balancer);
            if (Objects.equals(code, balancer.getUniqueCode())) {
                return balancer;
            }
        }
        throw new RpcException("invalid load balance config");
    }

}
