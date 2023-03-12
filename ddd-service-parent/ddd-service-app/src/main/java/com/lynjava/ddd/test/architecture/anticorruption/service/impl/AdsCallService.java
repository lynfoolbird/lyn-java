package com.lynjava.ddd.test.architecture.anticorruption.service.impl;


import com.lynjava.ddd.test.architecture.anticorruption.consts.URIEnum;
import com.lynjava.ddd.test.architecture.anticorruption.service.AbstractCallService;

public class AdsCallService extends AbstractCallService {
    public AdsCallService(){

    }

    public AdsCallService(URIEnum uri, Object param) {
        super(uri,param);
    }

    @Override
    public String getGateway() {
        return "https://localhost:443";
    }
}
