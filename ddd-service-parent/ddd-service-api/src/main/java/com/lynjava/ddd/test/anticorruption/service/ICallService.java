package com.lynjava.ddd.test.anticorruption.service;

public interface ICallService {
    String getGateway();

    String execute() throws Exception;
}
