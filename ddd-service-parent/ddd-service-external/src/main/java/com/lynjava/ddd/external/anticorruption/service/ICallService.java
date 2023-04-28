package com.lynjava.ddd.external.anticorruption.service;

public interface ICallService {
    String getGateway();

    String execute() throws Exception;
}
