package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static com.demo.kafka.PropertiesHelper.getProperties;

class SimpleConsumer {
    private static final int TIME_OUT_MS = 5000;
    static Logger log = Logger.getLogger(SimpleConsumer.class.getName());

    static void run(String topicName, int numOfRecords, KafkaMessageHandler callback) throws Exception {
        Properties props = getProperties();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.assign(Collections.singleton(new TopicPartition(topicName, 0)));

        int recNum = numOfRecords;

        while (recNum > 0) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(TIME_OUT_MS));
            if (records.count() == 0) {
                log.info(DataHelper.getSimpleJSONObject("No records retrieved"));
                break;
            }

            for (ConsumerRecord<String, String> record : records) {
                callback.processMessage(topicName, record);
                recNum--;
            }
        }

        consumer.close();
    }
}
