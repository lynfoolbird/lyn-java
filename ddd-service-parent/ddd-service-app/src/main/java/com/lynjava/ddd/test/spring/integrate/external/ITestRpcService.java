package com.lynjava.ddd.test.spring.integrate.external;

import com.lynjava.ddd.test.spring.integrate.RestCall;
import com.lynjava.ddd.test.spring.integrate.RestClient;

/**
 * 模拟远程调用，openfeign
 * @author li
 */
@RestClient(baseUrl = "http://localhost:8081/")
public interface ITestRpcService {

    @RestCall(path = "/job", method = "POST")
    Boolean sendJob(String job);

}
