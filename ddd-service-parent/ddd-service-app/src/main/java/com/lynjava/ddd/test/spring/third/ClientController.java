package com.lynjava.ddd.test.spring.third;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ThirdPartyQueryService thirdPartyQueryService;

    @GetMapping("/order")
    public String queryOrder(@RequestParam String orderId) {
        thirdPartyQueryService.findOrder(orderId);
        return "";
    }
}
