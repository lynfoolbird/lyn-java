package com.lynjava.limiter.conditon;

import com.lynjava.limiter.common.LimiterConsts;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LimitAspectCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //检查配置文件是否包含limit.type属性
        return context.getEnvironment().containsProperty(LimiterConsts.LIMIT_TYPE);

    }
}
