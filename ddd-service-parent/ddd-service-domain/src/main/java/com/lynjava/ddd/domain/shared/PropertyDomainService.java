package com.lynjava.ddd.domain.shared;

import javax.inject.Named;

@Named
public class PropertyDomainService {

    public String getValueWithDefault(String property, String defaultValue) {
        return defaultValue;
    }
}
