package com.lynjava.ddd.external.servicemarket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lynjava.ddd.common.model.ResponseDataWrapper;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import com.lynjava.ddd.domain.external.servicemarket.IServiceMarketExternalService;
import com.lynjava.ddd.domain.external.servicemarket.converter.ServiceMarketConverter;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtInDto;
import com.lynjava.ddd.domain.external.servicemarket.dto.OrderExtOutDto;
import com.lynjava.ddd.external.BaseExternalService;
import com.lynjava.ddd.external.common.consts.RequestURIEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ServiceMarketExternalServiceImpl extends BaseExternalService implements IServiceMarketExternalService {
    @Value("${sevice.market.gateway:http://dev.lyn.com}")
    private String serviceMarketGateway;
    @Inject
    private ServiceMarketConverter serviceMarketConverter;

    @Override
    public List<OrderExtOutDto> createOrder(ClusterAR clusterAR) {
        // DTO转换
        OrderExtInDto orderExtInDto = serviceMarketConverter.toInputDto(clusterAR);
        // 响应体
        String responseBody = sendRequest(RequestURIEnum.SERVICE_MARKET_CREATE_ORDER, JSON.toJSONString(orderExtInDto), "CREATE");
        ResponseDataWrapper<List<OrderExtOutDto>> response = JSON.parseObject(responseBody,
                new TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>>() {});
        ResponseDataWrapper<List<OrderExtOutDto>> response2 = JSON.parseObject(responseBody)
                .toJavaObject(new DataBaseOutputDtoTypeReference());
        return response.getData();
    }

    @Override
    public List<String> createOrder(List<ClusterAR> clusters) {
        String url = RequestURIEnum.SERVICE_MARKET_CREATE_ORDER.buildRequestUrl(getGateway(), "123");
        List<String> orderIds = sendRequest(url, RequestURIEnum.SERVICE_MARKET_CREATE_ORDER.getMethod(), clusters,
                new TypeReference<List<String>>() {});
        return orderIds;
    }

    @Override
    protected String getGateway() {
        return serviceMarketGateway;
    }

    @Override
    protected HttpHeaders buildHeaders() {
        HttpHeaders headers = super.buildHeaders();
        headers.add("Authorization", "token");
        return headers;
    }

    public static class DataBaseOutputDtoTypeReference extends TypeReference<ResponseDataWrapper<List<OrderExtOutDto>>> {
    }
}


