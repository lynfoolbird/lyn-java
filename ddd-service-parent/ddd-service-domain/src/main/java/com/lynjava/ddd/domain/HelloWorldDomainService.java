package com.lynjava.ddd.domain;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldDomainService {

    public String domprint() {
        return "domainService";
    }
}
