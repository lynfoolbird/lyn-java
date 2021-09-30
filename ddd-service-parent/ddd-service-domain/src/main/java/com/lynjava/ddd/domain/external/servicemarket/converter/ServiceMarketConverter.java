package com.lynjava.ddd.domain.external.servicemarket.converter;

import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderInputDto;

import javax.inject.Named;

/**
 * 防腐层dto与do转换类
 */
@Named
public class ServiceMarketConverter {

    public OrderInputDto toInputDto(ClusterAR clusterAR) {
        OrderInputDto orderInputDto = OrderInputDto.builder()
                .orderId("one" + clusterAR.getId())
                .orderName("produce")
                .quantity(100)
                .build();
        return orderInputDto;
    }
}
