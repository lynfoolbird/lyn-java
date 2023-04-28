package com.lynjava.ddd.external.anticorruption.service.impl;


import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.AbstractCallService;

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
