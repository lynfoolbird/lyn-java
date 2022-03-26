package com.lynjava.ddd.domain.cluster.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 值对象
 */
@Data
@Builder
public class TestVO {
    private String orderId;

    private String orderName;

    private Integer quantity;

}
