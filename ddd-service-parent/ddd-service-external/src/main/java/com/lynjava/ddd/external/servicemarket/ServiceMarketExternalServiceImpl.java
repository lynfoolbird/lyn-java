package com.lynjava.ddd.external.servicemarket;


import com.lynjava.ddd.domain.cluster.vo.OrderVO;
import com.lynjava.ddd.domain.external.IServiceMarketExternalService;
import com.lynjava.ddd.external.BaseExternalService;
import com.lynjava.ddd.common.consts.CommonConstants;

import javax.inject.Named;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {

    @Override
    public String createOrder(OrderVO orderVO) {
        return CommonConstants.SUCCESS + ":" + orderVO;
    }
}
