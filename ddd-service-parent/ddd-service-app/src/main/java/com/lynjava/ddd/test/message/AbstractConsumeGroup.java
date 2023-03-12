package com.lynjava.ddd.test.message;//package com.lyn.mvnprj.consumer;
//
//import kafka.consumer.Consumer;
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.message.MessageAndMetadata;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by liyanan on 2018/5/14.
// */
//public abstract class AbstractConsumeGroup {
//    @Value("${kafka.zk.server}")
//    private String zkServers = "192.168.1.100:100,192.168.1.101:100";
//    private String kafkaConsumerGroupId = "live";
//
//    private ConsumerConnector connector;
//    private ExecutorService executor;
//
//    protected abstract boolean isOpen();
//    protected abstract String getConsumerTopic();
//    protected long consumerSpeedByMills(){
//        return TimeUnit.MILLISECONDS.toMillis(100);
//    }
//    protected int getConsumerThreadQuantity(){
//        return 1;
//    }
//    protected abstract void processTopicContent(String kafkaContent);
//    @PostConstruct
//    public void init(){
//        if (isOpen()) {
//            createConnection();
//            consume();
//        }
//    }
//    @PreDestroy
//    public void destroy(){
//        if (null != executor) {
//            executor.shutdown();
//        }
//        try {
//            executor.awaitTermination(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (null != connector) {
//            connector.shutdown();
//        }
//    }
//    public void createConnection(){
//        Properties props = new Properties();
//        props.put("zookeeper.connect", zkServers);
//        props.put("group.id", kafkaConsumerGroupId);
//        ConsumerConfig consumerConfig = new ConsumerConfig(props);
//        connector = Consumer.createJavaConsumerConnector(consumerConfig);
//    }
//    public void consume(){
//        Map<String, Integer> topicMap = new HashMap<String, Integer>();
//        topicMap.put(getConsumerTopic(), getConsumerThreadQuantity());
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector.createMessageStreams(topicMap);
//
//        executor = Executors.newCachedThreadPool();
//        List<KafkaStream<byte[], byte[]>> streams =consumerMap.get(getConsumerTopic());
//        for (KafkaStream<byte[], byte[]> stream:streams) {
//            executor.submit(new ConsumerThread(stream));
//        }
//    }
//
//    private class ConsumerThread implements Runnable {
//        private KafkaStream<byte[], byte[]> stream;
//        public ConsumerThread(KafkaStream<byte[], byte[]> stream) {
//            this.stream = stream;
//        }
//        public void run() {
//            ConsumerIterator<byte[], byte[]> iterator = this.stream.iterator();
//            while (iterator.hasNext()) {
//                try {
//                    MessageAndMetadata<byte[], byte[]> msg = iterator.next();
//                    String content = new String(msg.message(), "UTF-8");
//                    processTopicContent(content);
//                    TimeUnit.MILLISECONDS.sleep(consumerSpeedByMills());
//                } catch (Throwable t) {
//                    t.printStackTrace();
//                }
//            }
//        }
//    }
//}
