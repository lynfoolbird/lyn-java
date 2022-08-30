package com.lynjava.ddd.external.servicemarket;


import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.external.BaseExternalService;
import com.lynjava.ddd.common.consts.CommonConstants;

import javax.inject.Named;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {

    @Override
    public String createOrder(OrderExtInDto orderExtInDto) {
        return CommonConstants.SUCCESS + ":" + orderExtInDto;
    }
}
