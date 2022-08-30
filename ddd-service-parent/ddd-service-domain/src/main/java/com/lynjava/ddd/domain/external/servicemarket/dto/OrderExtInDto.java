package com.lynjava.ddd.domain.external.servicemarket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderExtInDto {
    private String orderId;

    private String orderName;

    private Integer quantity;
}
