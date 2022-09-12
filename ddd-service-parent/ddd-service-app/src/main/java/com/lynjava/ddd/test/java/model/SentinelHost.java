package com.lynjava.ddd.test.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public  class SentinelHost {
    private String hostName;
    private Integer port;
    private String sentinelClusterId;
}