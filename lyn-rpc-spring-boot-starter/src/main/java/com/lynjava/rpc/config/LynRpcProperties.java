package com.lynjava.rpc.config;

import com.lynjava.rpc.core.consts.RpcConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author li
 */
@ConfigurationProperties(prefix = RpcConstants.CONFIG_PREFIX)
@Getter
@Setter
public class LynRpcProperties {
    private Boolean enable;

    private List<String> active;
    /**
     *  应用名称(必填)
     */
    private String appName;

    /**
     * 环境标识
     */
    private String usf;

    /**
     * 服务注册中心地址
     */
    private String registerAddress = "127.0.0.1:2181";

    /**
     * 服务暴露端口
     */
    private Integer port = 9999;

    private String serializer;

    /**
     * 负载均衡算法
     */
    private String balancer;
    /**
     * 权重，默认为1
     */
    private Integer weight = 1;

    private Integer timeout;
}
