package com.lynjava.ddd.test.architecture.anticorruption.service;

public interface ICallService {
    String getGateway();

    String execute() throws Exception;
}
