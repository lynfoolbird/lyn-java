package com.lynjava.ddd.domain.external.servicemarket.converter;

import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;

import javax.inject.Named;

/**
 * 防腐层dto与do转换类
 */
@Named
public class ServiceMarketConverter {

    public OrderExtInDto toInputDto(ClusterAR clusterAR) {
        OrderExtInDto orderExtInDto = OrderExtInDto.builder()
                .orderId("one" + clusterAR.getId())
                .orderName("produce")
                .quantity(100)
                .build();
        return orderExtInDto;
    }

    public ClusterAR toDO(OrderExtOutDto orderExtOutDto) {
        return ClusterAR.builder().id(orderExtOutDto.getOrderId()).build();
    }
}
