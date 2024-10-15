package com.lynjava.ddd.common.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ParseContext;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;

public class JsonParseContext {
    private volatile static ParseContext parseContext;


    public static ParseContext getParseContext() {
        if (parseContext == null) {
            synchronized (JsonParseContext.class) {
                if (parseContext == null) {
//                    // jsonpath默认的jsonprovider会限制数组长度; 浮点数精度问题
//                    Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
                    Configuration conf = Configuration.builder()
                            .jsonProvider(new JacksonJsonProvider(objectMapper))
                            .options(Option.DEFAULT_PATH_LEAF_TO_NULL)
                            .build();
                    parseContext = JsonPath.using(conf);
                }
            }
        }
        return parseContext;
    }
}
