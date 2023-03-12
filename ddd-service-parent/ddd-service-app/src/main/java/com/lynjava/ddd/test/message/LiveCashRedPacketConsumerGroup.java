package com.lynjava.ddd.test.message;//package com.lyn.mvnprj.consumer;
//
//import com.alibaba.fastjson.JSON;
//import com.lyn.dto.LiveCashRedPacketDataDTO;
//import org.springframework.stereotype.Component;
//
///**
// * Created by liyanan on 2018/5/14.
// */
//@Component("liveCashRedPacketConsumerGroup")
//public class LiveCashRedPacketConsumerGroup extends AbstractConsumeGroup {
//    protected boolean isOpen() {
//        return true;
//    }
//
//    protected String getConsumerTopic() {
//        return "LIVE_CASH_RED_PACKET_DATA";
//    }
//
//    protected void processTopicContent(String kafkaContent) {
////        LiveCashRedPacketDataDTO data = JSON.parseObject(kafkaContent, LiveCashRedPacketDataDTO.class);
////        System.out.println(data);
//    }
//}
