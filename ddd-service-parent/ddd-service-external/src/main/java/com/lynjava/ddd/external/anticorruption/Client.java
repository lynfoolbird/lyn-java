package com.lynjava.ddd.external.anticorruption;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lynjava.ddd.external.anticorruption.consts.URIEnum;
import com.lynjava.ddd.external.anticorruption.service.impl.AlbCallService;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) throws Exception {
        Map map = new HashMap();
        map.put("hello",null);
        map.put("world", "ok");
        System.out.println(JSON.toJSONString(map, SerializerFeature.WRITE_MAP_NULL_FEATURES));
        String result = CallServiceBuilder.create()
                .setUri(URIEnum.ALB_SYNC_STATIC_ROUTER)
                .setParam("hello")
                .buildCallService(AlbCallService.class)
                .execute();
        System.out.println(result);
        System.out.println("hello world");
        String result2 = CallServiceFactory
                .createCallService(AlbCallService.class, URIEnum.ALB_SYNC_STATIC_ROUTER, "google")
                .execute();
        System.out.println(result2);
        String result3 = CallServiceFactory.createCallService(AlbCallService.class)
                        .setUri(URIEnum.ALB_SYNC_STATIC_ROUTER)
                        .setParam("")
                        .execute();
        System.out.println(result3);
    }
}
