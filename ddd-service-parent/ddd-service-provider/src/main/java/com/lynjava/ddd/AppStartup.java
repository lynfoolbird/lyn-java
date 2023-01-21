package com.lynjava.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * 应用启动类
 *
 * @author li
 */
@SpringBootApplication(scanBasePackages = {"com.lynjava.ddd"})
//@ComponentScan(basePackages ={ "com.lynjava.ddd"})
@ImportResource(locations = { "classpath:config/*.xml" })
public class AppStartup extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppStartup.class);
    }
}
