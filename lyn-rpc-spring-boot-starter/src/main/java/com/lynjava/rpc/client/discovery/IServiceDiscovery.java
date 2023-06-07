package com.lynjava.rpc.client.discovery;

import com.lynjava.rpc.core.model.ServiceInfo;

import java.util.List;

/**
 * 服务发现抽象类
 *
 * @author li
 */
public interface IServiceDiscovery {

    List<ServiceInfo> findServiceList(String serviceName);
}
