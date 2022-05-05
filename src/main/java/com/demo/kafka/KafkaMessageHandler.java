package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

@FunctionalInterface
public interface KafkaMessageHandler {
    void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception;

}
