package com.lynjava.rpc.condition;

import com.lynjava.rpc.config.LynRpcProperties;
import com.lynjava.rpc.core.consts.RpcConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 启用RpcClient的条件
 *
 * @author li
 */
public class EnableRpcClientCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 无法获取到
        List lynRpcEnableList = context.getEnvironment()
                .getProperty(RpcConstants.CONFIG_PREFIX + "." + "active", List.class);
        LynRpcProperties rpcProperties = Objects.requireNonNull(context.getBeanFactory())
                .getBean(LynRpcProperties.class);
        return !CollectionUtils.isEmpty(rpcProperties.getActive())
                && rpcProperties.getActive().contains("client");
    }
}
