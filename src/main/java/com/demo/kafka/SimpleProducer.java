package com.demo.kafka;

import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

import java.util.Properties;

import static com.demo.kafka.PropertiesHelper.getProperties;

import org.apache.log4j.Logger;

class SimpleProducer {
    public SimpleProducer(String topicName) throws Exception {
        setTopicName(topicName);
        outputPropertiesValue();
    }

    private KafkaProducer<String, String> kafkaProducer;

    static Logger log = Logger.getLogger(SimpleProducer.class.getName());

    public void run(int numberOfMessages) throws Exception {
        int i = 0;
        while (i <= numberOfMessages) {
            String key = UUID.randomUUID().toString();
            String message = DataHelper.getRandomString();
            this.send(key, message);
            i++;
            Thread.sleep(100);
        }
        this.close();

    }

    private String topicName = null;

    private void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private String getTopicName() {
        return this.topicName;
    }

    protected void send(String key, String message) throws Exception {
        String topicName = this.getTopicName();
        String source = SimpleProducer.class.getName();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(this.getTopicName(), key, message);
        JSONObject obj = DataHelper.getMessageLogEntryJSON(source, topicName, key, message);
        log.info(obj.toJSONString());
        getKafkaProducer().send(producerRecord);
    }

    public void close() {
        try {
            getKafkaProducer().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private KafkaProducer<String, String> getKafkaProducer() throws Exception {
        if (this.kafkaProducer == null) {
            Properties props = getProperties();
            this.kafkaProducer = new KafkaProducer<String, String>(props);
        }
        return this.kafkaProducer;
    }

    private void setKafkaProducer(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    private void outputPropertiesValue() throws Exception {
        Properties props = getProperties();
        System.out.println(props.getProperty("enable.auto.commit"));
        System.out.println(props.getProperty("default.topic"));
        System.out.println(props.getProperty("bootstrap.servers"));
        System.out.println(props.getProperty("key.serializer"));
        System.out.println(props.getProperty("value.serializer"));
        System.out.println(props.getProperty("key.deserializer"));
        System.out.println(props.getProperty("value.deserializer"));
        System.out.println(props.getProperty("group.id"));
    }

}
