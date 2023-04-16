package com.lynjava.limiter.common;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Limiter {
    private String key;

    private Integer permitsPerSecond;
}
