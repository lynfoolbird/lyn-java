package com.lynjava.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"com.lynjava.ddd"})
//@ComponentScan(basePackages ={ "com.lynjava.ddd"})
@ImportResource(locations = { "classpath:config/*.xml" })
public class DddServiceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DddServiceApplication.class);
    }
}
