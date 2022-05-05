package com.demo.kafka;

import java.util.Properties;
import java.util.Random;
import org.json.simple.JSONObject;

import static com.demo.kafka.PropertiesHelper.getProperties;

public class DataHelper {

    private static Properties props;

    public static String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static JSONObject getMessageLogEntryJSON(String source, String topic, String key, String message) throws Exception {
        JSONObject obj = new JSONObject();
        String bootstrapServers = getProperties().getProperty("bootstrap.servers");
        obj.put("bootstrapServers", bootstrapServers);
        obj.put("source", source);
        obj.put("topic", topic);
        obj.put("key", key);
        obj.put("message", message);

        return obj;
    }

    public static JSONObject getSimpleJSONObject(String message) throws Exception {
        JSONObject obj = new JSONObject();
        String bootstrapServers = getProperties().getProperty("bootstrap.servers");
        obj.put("message", message);
        return obj;
    }

    protected static Properties getProperties() throws Exception {
        if (props == null){
            props = PropertiesHelper.getProperties();
        }

        return props;
    }


}
