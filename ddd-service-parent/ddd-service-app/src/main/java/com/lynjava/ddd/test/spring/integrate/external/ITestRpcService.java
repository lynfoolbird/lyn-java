package com.lynjava.ddd.test.spring.integrate.external;

import com.lynjava.ddd.common.model.BaseResponse;
import com.lynjava.ddd.test.spring.integrate.RestCall;
import com.lynjava.ddd.test.spring.integrate.RestClient;

/**
 * 模拟远程调用，openfeign
 * @author li
 */
@RestClient(baseUrl = "http://localhost:8081/ddd_service")
public interface ITestRpcService {

    @RestCall(path = "/test/test5.do", method = "POST")
    BaseResponse sendJob(String job);

}
