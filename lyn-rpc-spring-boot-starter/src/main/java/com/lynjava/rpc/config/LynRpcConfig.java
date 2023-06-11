package com.lynjava.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li
 */
@ConfigurationProperties(prefix = "lyn.rpc")
@Data
public class LynRpcConfig {
    /**
     *  应用名称
     */
    private String appName;

    /**
     * 服务注册中心地址
     */
    private String registerAddress = "127.0.0.1:2181";

    /**
     * 服务暴露端口
     */
    private Integer port = 9999;
    /**
     * 服务协议
     */
    private String protocol = "java";
    /**
     * 负载均衡算法
     */
    private String loadBalance = "random";
    /**
     * 权重，默认为1
     */
    private Integer weight = 1;

    private Integer timeout;

    private String serializer;


}
