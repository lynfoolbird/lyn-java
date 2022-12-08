package com.lynjava.ddd.external.servicemarket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "amazon.s3")
@Getter
@Setter
public class AmazonS3Config {

    private String ak;

    private String sk;

    @Bean
    public String s3Client() {
        return "";
    }
}
