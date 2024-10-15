package com.lynjava.ddd.common.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FilePathUtils {
    private static final String CONFIG_PATH = "classpath:gateway_config/"
            + "t_protocol_config" + File.separator + "*" + ".json";

    static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    static {
        System.out.println("hellokkkk");

        try {
            Resource[] resources = resolver.getResources(CONFIG_PATH);
            for (Resource resource :resources) {
                String fileName = Objects.requireNonNull(resource.getFilename())
                        .replaceAll(".json", "");
                if (resource.exists()) {
                    String json = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                    System.out.println("fileName is " + fileName);
                    System.out.println("json is " + json);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
