package com.lynjava.ddd;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统计每个bean的加载时间，用于优化启动耗时；正式发布时删除该代码
 */
@Component
public class TimeCostBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Long> costMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        costMap.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (costMap.containsKey(beanName)) {
            long cost = System.currentTimeMillis() - costMap.get(beanName);
            if (cost > 0) {
                System.out.println("============ bean: " + beanName + ", costMillis: " + cost);
            }
        }
        return bean;
    }
}
