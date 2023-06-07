package com.lynjava.rpc.server.register;


import com.lynjava.rpc.core.model.ServiceInfo;

import java.io.IOException;

/**
 * 服务注册抽象类
 *
 * @author li
 */
public interface IServiceRegister {

    void register(ServiceInfo serviceInfo) throws Exception;

    void unRegister(ServiceInfo serviceInfo) throws Exception;

    void destroy() throws IOException;

}
