package com.lynjava.ddd.external.servicemarket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.RequestDataWrapper;
import com.lynjava.ddd.common.model.ResponseDataWrapper;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;
import com.lynjava.ddd.domain.external.servicemarket.converter.ServiceMarketConverter;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;
import com.lynjava.ddd.external.BaseExternalService;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {

    @Inject
    private RestTemplate restTemplate;

    @Inject
    private ServiceMarketConverter serviceMarketConverter;

    @Override
    public List<OrderExtOutDto> createOrder(ClusterAR clusterAR) {
        // DTO转换
        OrderExtInDto orderExtInDto = serviceMarketConverter.toInputDto(clusterAR);
        // 包装body
        RequestDataWrapper<OrderExtInDto> body = RequestDataWrapper.<OrderExtInDto>builder()
                .type("CLUSTER")
                .data(RequestDataWrapper.Data.<OrderExtInDto>builder().attributes(orderExtInDto).build())
                .build();
        JSONObject json = new JSONObject();
        json.put("id", "001");
        json.put("type", "rule");
        Map<String, String> map = new HashMap<>();
        map.put("orderId", "id00001");
        json.put("data", Arrays.asList(map));
        ResponseDataWrapper<List<OrderExtOutDto>> response = JSON.parseObject(json.toJSONString(),
                new TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>>() {});
        ResponseDataWrapper<List<OrderExtOutDto>> response2 = json.toJavaObject(new DataBaseOutputDtoTypeReference());
        return response.getData();
    }

    public static class DataBaseOutputDtoTypeReference extends TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>> {
    }
}


