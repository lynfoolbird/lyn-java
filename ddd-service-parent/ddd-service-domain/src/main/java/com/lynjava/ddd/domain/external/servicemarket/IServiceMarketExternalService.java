package com.lynjava.ddd.domain.external.servicemarket;

import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;

/**
 * 防腐层接口在domain层声明，由于application层依赖domain层，所以也是可以使用的
 */
public interface IServiceMarketExternalService {

    String createOrder(OrderExtInDto orderExtInDto);
}
