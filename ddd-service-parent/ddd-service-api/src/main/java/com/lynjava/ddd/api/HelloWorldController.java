package com.lynjava.ddd.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctl")
public class HelloWorldController {

    @GetMapping("/")
    public String print() {
        return "Hello world!2";
    }
}
