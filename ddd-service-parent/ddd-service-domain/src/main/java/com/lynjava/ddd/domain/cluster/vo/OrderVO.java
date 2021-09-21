package com.lynjava.ddd.domain.cluster.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderVO {
    private String orderId;

    private String orderName;

    private Integer quantity;

}
