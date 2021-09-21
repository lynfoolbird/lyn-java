package com.lynjava.ddd.app;


import com.lynjava.ddd.domain.HelloWorldDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldAppService {

    @Autowired
    private HelloWorldDomainService helloWorldDomainService;

    public String pp() {
        return helloWorldDomainService.domprint();
    }

}
