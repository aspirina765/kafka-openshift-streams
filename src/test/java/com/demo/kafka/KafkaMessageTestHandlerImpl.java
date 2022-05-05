package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;

public class KafkaMessageTestHandlerImpl implements KafkaMessageHandler{

    static Logger log = Logger.getLogger(KafkaMessageHandlerImpl.class.getName());

    @Override
    public void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception {
        Assert.assertNotNull(message);
        String position = message.partition() + "-" + message.offset();

        Assert.assertEquals(message.key().getClass(),String.class);
        Assert.assertEquals(message.value().getClass(),String.class);
        Assert.assertEquals(topicName, message.topic());

        String source = KafkaMessageHandlerImpl.class.getName();
        JSONObject obj = DataHelper.getMessageLogEntryJSON(source, topicName,message.key(),message.value());
        log.info(obj.toJSONString());
    }
}