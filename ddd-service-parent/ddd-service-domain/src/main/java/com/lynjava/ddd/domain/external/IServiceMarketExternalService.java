package com.lynjava.ddd.domain.external;

import com.lynjava.ddd.domain.cluster.vo.OrderVO;

/**
 * 防腐层接口在domain层声明，由于application层依赖domain层，所以也是可以使用的
 */
public interface IServiceMarketExternalService {

    String createOrder(OrderVO orderVO);
}
