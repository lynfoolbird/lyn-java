package com.lynjava.ddd.test.service.impl;

import com.lynjava.ddd.test.service.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {
    @Override
    public String sayHello(String str) {
        return "hello world, " + str;
    }
}
