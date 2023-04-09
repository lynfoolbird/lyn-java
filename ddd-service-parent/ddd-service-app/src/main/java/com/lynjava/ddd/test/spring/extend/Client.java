package com.lynjava.ddd.test.spring.extend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class Client {

    @Autowired
    private ThirdPartyQueryService thirdPartyQueryService;

    @GetMapping("/")
    public String queryOrder(@RequestParam("orderId") String orderId) {
        thirdPartyQueryService.findOrder(orderId);
        return "";
    }
}
