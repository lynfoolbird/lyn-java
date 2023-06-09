package com.lynjava.ddd;

import com.lynjava.ddd.test.spring.third.ThirdPartyServiceScan;
import com.lynjava.limiter.annotation.EnableLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 应用启动类
 *
 * @author li
 */
@SpringBootApplication(scanBasePackages = {"com.lynjava.ddd"})
//@ComponentScan(basePackages ={ "com.lynjava.ddd"})
@ThirdPartyServiceScan({"com.lynjava.ddd.test.spring.extend"})
@EnableLock
@EnableAsync
@ImportResource(locations = { "classpath:config/*.xml" })
public class AppStartup extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppStartup.class);
    }
}
