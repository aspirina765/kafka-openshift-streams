package com.demo.kafka;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

public class PropertiesHelper {

    static Logger log = Logger.getLogger(PropertiesHelper.class.getName());

    static Dotenv dotenv = null;

    public static Properties getProperties() throws Exception {

        Properties props = null;
        try (InputStream input = SimpleProducer.class.getClassLoader().getResourceAsStream("config.properties")) {

            props = new Properties();

            if (input == null) {
                throw new Exception("Sorry, unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            props.load(input);
            props = addSecurityProperties(props);

            //get the property value and print it out
            for (Object key: props.keySet()) {
                log.info(key + ": " + props.getProperty(key.toString()));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }

    private static Dotenv getDotEnv() {
        if (dotenv == null) dotenv = Dotenv.configure().load();
        return dotenv;
    }

    static Properties addSecurityProperties(Properties props) throws PropertiesConfigurationException {
        // use the private method that ensures the required environment variables are present
        testForSecurityProperties();
        // create an instance of java-dotenv to retrieve the environment variables
        Dotenv env = getDotEnv();
        //override the local Kafka instance settings with the Kafka Stream instance
        props.put("bootstrap.servers", env.get("KAFKA_STREAM_BOOTSTRAP_SERVER"));
        // add the security settings
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        // create the connection string, getting username and pwd from env vars
        props.put("sasl.jaas.config", PlainLoginModule.class.getName() + " required username=\""
                + env.get("KAFKA_STREAM_USER_NAME") + "\" password=\"" + env.get("KAFKA_STREAM_PWD") + "\";");
        // return the amended properties collection
        return props;
    }

    static void testForSecurityProperties() throws PropertiesConfigurationException {
        Vector<String> errorMessages = new Vector<>();
        Dotenv env = getDotEnv();
        if (env.get("KAFKA_STREAM_USER_NAME") == null) errorMessages.add("KAFKA_STREAM_USER_NAME");
        if (env.get("KAFKA_STREAM_PWD") == null) errorMessages.add("KAFKA_STREAM_PWD");
        if (env.get("KAFKA_STREAM_BOOTSTRAP_SERVER") == null) errorMessages.add("KAFKA_STREAM_BOOTSTRAP_SERVER");

        if (errorMessages.size() > 0) {
            StringBuilder missingEnvVars = new StringBuilder();
            for (String s : errorMessages) {
                missingEnvVars.append(s).append(", ");
                String message = "The following required environment variables are missing: " + missingEnvVars;
                throw new PropertiesConfigurationException(message.substring(0, message.length() - 2));
            }
        }
    }
}
