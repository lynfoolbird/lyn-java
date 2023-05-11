package com.lynjava.ddd.test.spring.third;

@ThirdPartyService(handler = CommonThirdPartyServiceHandler.class)
public interface ThirdPartyQueryService {
    /**
     * 查询用户信息
     */
    void findUser(String userId);

    /**
     * 查询订单信息
     */
    void findOrder(String orderId);
}
