package com.demo.kafka;

import org.apache.kafka.clients.admin.TopicListing;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimpleProducerConsumerTest {
    private final String fixedTopicName = "mycooltopic";
    @BeforeClass
    public void before_class() throws Exception {
        //String topicName = KafkaTopicHelper.createRandomTopic();
    }

    @Test
    public void canGetFixedTopic() throws Exception {
        //go in once
        TopicListing result1 = KafkaTopicHelper.getFixedTopic(fixedTopicName);
        //go in again
        TopicListing result2 = KafkaTopicHelper.getFixedTopic(fixedTopicName);
        Assert.assertNotNull(result1);
        Assert.assertNotNull(result2);
        Assert.assertEquals(result1.topicId(),result2.topicId());

    }

    @Test
    public void canProduceConsumeStreamTest() throws Exception {
        //Create a brand new topic
        //KafkaTopicHelper.createFixedTopic(fixedTopicName);

        //Wait for Kafka to catch up with the topic creation before producing
        //Thread.sleep(3000);

        //create messages
        int messageCount = 20;
        SimpleProducer producer = new SimpleProducer(fixedTopicName);
        producer.run(messageCount);

        //Wait for Kafka to catch up before consuming messages
        Thread.sleep(1000);

        //consume the messages
        SimpleConsumer.run(fixedTopicName, messageCount, new KafkaMessageTestHandlerImpl());
    }
}
