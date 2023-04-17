package com.lynjava.ddd.external.servicemarket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.RequestDataWrapper;
import com.lynjava.ddd.common.model.ResponseDataWrapper;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;
import com.lynjava.ddd.domain.external.servicemarket.converter.ServiceMarketConverter;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;
import com.lynjava.ddd.external.BaseExternalService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {

    @Inject
    private ServiceMarketConverter serviceMarketConverter;

    @Override
    public List<OrderExtOutDto> createOrder(ClusterAR clusterAR) {
        // DTO转换
        OrderExtInDto orderExtInDto = serviceMarketConverter.toInputDto(clusterAR);
        // 请求体
        RequestDataWrapper<OrderExtInDto> body = RequestDataWrapper.<OrderExtInDto>builder()
                .type("CLUSTER")
                .data(RequestDataWrapper.Data.<OrderExtInDto>builder().attributes(orderExtInDto).build())
                .build();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("ContentType", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(body), headers);
        // 发送rest请求
        ResponseEntity<String> responseEntity = restTemplate
                .exchange("url", HttpMethod.POST, httpEntity, String.class);
        // 响应码
        int responseCode = responseEntity.getStatusCodeValue();
        // 响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        // 响应体
        String responseBody = responseEntity.getBody();
        ResponseDataWrapper<List<OrderExtOutDto>> response = JSON.parseObject(responseBody,
                new TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>>() {});
        ResponseDataWrapper<List<OrderExtOutDto>> response2 = JSON.parseObject(responseBody)
                .toJavaObject(new DataBaseOutputDtoTypeReference());
        return response.getData();
    }

    @Override
    public List<String> createOrder(List<ClusterAR> clusters) {
        List<String> orderIds = sendRequest("url", HttpMethod.POST, clusters, new TypeReference<List<String>>() {
        });
        return orderIds;
    }

    public static class DataBaseOutputDtoTypeReference extends TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>> {
    }
}


