package com.lynjava.ddd.domain.external.servicemarket;

import com.lynjava.ddd.common.model.ResponseDataWrapper;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;

import java.util.List;

/**
 * 防腐层接口在domain层声明，由于application层依赖domain层，所以也是可以使用的
 */
public interface IServiceMarketExternalService {

    ResponseDataWrapper<List<OrderExtOutDto>> createOrder(OrderExtInDto orderExtInDto);
}
