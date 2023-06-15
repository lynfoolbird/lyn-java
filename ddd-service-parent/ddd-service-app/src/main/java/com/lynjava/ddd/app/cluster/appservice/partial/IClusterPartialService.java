package com.lynjava.ddd.app.cluster.appservice.partial;

public interface IClusterPartialService {
    Object process(Integer clusterId, String body);
}
