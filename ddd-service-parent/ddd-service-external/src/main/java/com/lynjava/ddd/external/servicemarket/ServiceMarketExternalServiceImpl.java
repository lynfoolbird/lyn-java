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
public class ServiceMarketExternalServiceImpl extends BaseExternalService
        implements IServiceMarketExternalService {
    @Value("${sevice.market.gateway:http://dev.lyn.com}")
    private String serviceMarketGateway;
    @Inject
    private ServiceMarketConverter serviceMarketConverter;

    @Override
    public ClusterAR createOrder(ClusterAR clusterAR) {
        // DTO转换
        OrderExtInDto orderExtInDto = serviceMarketConverter.toInputDto(clusterAR);
        // 响应体
        OrderExtOutDto response = new OrderExtOutDto();
        try {
            response = sendRequest(RequestURIEnum.SERVICE_MARKET_CREATE_ORDER, orderExtInDto, OrderExtOutDto.class,"CREATE");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // DTO转换
        return serviceMarketConverter.toDO(response);
    }

    @Override
    public List<OrderExtOutDto> createOrder(List<ClusterAR> clusters) {
        String url = RequestURIEnum.SERVICE_MARKET_CREATE_ORDER.buildRequestUrl(getGateway(), "123");
        List<OrderExtOutDto> orders = sendRequest(url, RequestURIEnum.SERVICE_MARKET_CREATE_ORDER.getMethod(), clusters,
                new TypeReference<List<OrderExtOutDto>>() {});

        String resStr = sendRequest(url, RequestURIEnum.SERVICE_MARKET_CREATE_ORDER.getMethod(), clusters,
                String.class);
        ResponseDataWrapper<List<OrderExtOutDto>> wrapper = JSON.parseObject(resStr, new DataBaseOutputDtoTypeReference());
        orders = wrapper.getData();
        return orders;
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


