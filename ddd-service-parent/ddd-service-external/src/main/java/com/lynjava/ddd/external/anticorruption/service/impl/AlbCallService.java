package com.lynjava.ddd.external.anticorruption.service.impl;


import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.AbstractCallService;

public class AlbCallService extends AbstractCallService {

    public AlbCallService(){
        super();
    }

    public AlbCallService(URIEnum uri, Object param){
        super(uri, param);
    }

    @Override
    public String getGateway() {
        return "http://localhost:8080";
    }
}
