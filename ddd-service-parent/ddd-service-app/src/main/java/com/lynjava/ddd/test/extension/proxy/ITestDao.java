package com.lynjava.ddd.test.extension.proxy;

import java.util.List;

public interface ITestDao {

    List<String> select(String param);

    int insert(String param);

}
