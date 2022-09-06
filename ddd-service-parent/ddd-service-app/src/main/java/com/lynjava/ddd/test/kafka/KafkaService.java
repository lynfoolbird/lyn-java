package com.lynjava.ddd.test.kafka;//package com.lyn.mvnprj.appservice.kafka;
//
//import com.alibaba.fastjson.JSON;
//import com.lyn.consts.KafkaTopicConst;
//import com.lyn.dto.LiveCashRedPacketDataDTO;
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
//
//import javax.annotation.PostConstruct;
//import java.util.Properties;
//
///**
// * Created by liyanan on 2018/5/20.
// */
//public class KafkaService {
//    private String METADATA_BROKER_LIST;
//
//    private Producer<String, String> syncProducer;
//
//    private Producer<String, String> asyncProducer;
//
//    @PostConstruct
//    public void init(){
//        Properties syncProperties = new Properties();
//        syncProperties.put("metadata.broker.list", METADATA_BROKER_LIST);
//        syncProperties.put("serializer.class", "kafka.serializer.StringEncoder");
//        syncProperties.put("request.required.acks", "1");
//        syncProperties.put("producer.type", "sync");
//
//        syncProducer = new Producer<String, String>(new ProducerConfig(syncProperties));
//
//        Properties asyncProperties = new Properties();
//        asyncProperties.put("metadata.broker.list", METADATA_BROKER_LIST);
//        asyncProperties.put("serializer.class", "kafka.serializer.StringEncoder");
//        asyncProperties.put("request.required.acks", "1");
//        asyncProperties.put("producer.type", "async");
//        asyncProperties.put("compression.codec", "snappy");
//        asyncProperties.put("queue.buffering.max.messages", "2000");
//
//        asyncProducer = new Producer<String, String>(new ProducerConfig(asyncProperties));
//
//    }
//
//    public void sendLiveCashRedPacketMsg(LiveCashRedPacketDataDTO dto, boolean isSync) {
//        KeyedMessage<String, String> data = new KeyedMessage<String, String>(KafkaTopicConst.TOPIC_LIVE_CASH_REDPACKET_DATA, JSON.toJSONString(dto));
//        if (isSync) {
//            syncProducer.send(data);
//        } else {
//            asyncProducer.send(data);
//        }
//    }
//}
//
//
//
