package com.lynjava.ddd.test.architecture.designpattern.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvokeResult {
    private String code;

    private String message;

    private Object result;
}
