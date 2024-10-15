package com.lynjava.ddd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 统计启动事件监听，用于优化启动耗时；正式发布时删除该代码
 */
@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("--------------------- event received:{}", event.getClass().getName());
    }
}
