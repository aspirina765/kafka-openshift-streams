package com.demo.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KafkaTopicHelper {

    public static TopicListing getFixedTopic(String topicName) throws Exception {
        Properties props = PropertiesHelper.getProperties();
        Admin admin = Admin.create(props);
        //if the topic exists, if not make it
        ListTopicsResult topics = admin.listTopics();

        for (TopicListing listing : topics.listings().get()) {
            if(new String(listing.name()).equals(topicName)) return listing;
        }
        return null;
    }
    public static String createRandomTopic() throws Exception {

        Properties props = PropertiesHelper.getProperties();
        Admin admin = Admin.create(props);
        String newTopicName = getCurrentUtcTimestamp() + DataHelper.getRandomString();
        int partitions = 1;
        short replicationFactor = 1;
        NewTopic newTopic = new NewTopic(newTopicName, partitions, replicationFactor);

        CreateTopicsResult result = admin.createTopics(
                Collections.singleton(newTopic)
        );

        KafkaFuture<Void> future = result.values().get(newTopicName);
        future.get();

        return newTopicName;
    }

    private static String getCurrentUtcTimestamp() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date());
    }
}
