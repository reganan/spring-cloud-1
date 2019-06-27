package com.cloud.example.rocketmq.scheduledmessage;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 发送延时消息
 *
 * @author Benji
 * @date 2019-06-27
 */
public class ScheduledMessageProducer {

    /**
     * <p>org/apache/rocketmq/store/config/MessageStoreConfig.java
     * </p>
     * private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        int totalMessagesToSend = 10;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            // 发送消息
            producer.send(message);

            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), message);
        }

        producer.shutdown();
    }

}