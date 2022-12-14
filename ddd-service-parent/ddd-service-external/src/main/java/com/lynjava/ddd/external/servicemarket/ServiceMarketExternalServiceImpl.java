package com.lynjava.ddd.external.servicemarket;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.DataBaseOutputDto;
import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;
import com.lynjava.ddd.external.BaseExternalService;
import com.lynjava.ddd.common.consts.CommonConstants;

import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {

    @Override
    public DataBaseOutputDto<List<OrderExtOutDto>> createOrder(OrderExtInDto orderExtInDto) {
        JSONObject json = new JSONObject();
        json.put("id", "001");
        json.put("type", "rule");
        Map<String, String> map = new HashMap<>();
        map.put("orderId", "id00001");
        json.put("data", Arrays.asList(map));
        DataBaseOutputDto<List<OrderExtOutDto>> out = json.toJavaObject(new DataBaseOutputDtoTypeReference());
        return out;
    }

    public static class DataBaseOutputDtoTypeReference extends TypeReference<DataBaseOutputDto<List<OrderExtOutDto>>> {
    }
}


