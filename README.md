# kafka-openshift-streams-demo
A project that demonstrates how to run the Apache Kafka Client against a Kafka broker running under Red Hat OpenShift Streams for Apache Kafka

## What you need to know before you start

* Java programming with Maven
* Access to the Red Hat OpenShift Console
* Basic understanding of message production and consumption under Kafka

## Setting up a Kafka instance and a Kafka topic in Red Hat Open Shift
TO BE provided

## Configuring the .env file
This project uses [`java-dotenv`](https://github.com/cdimascio/java-dotenv) to emulate environment variables and their values a runtime.

`java-dotenv` is already installed in the project's `pom.xml` file.

However, you will need to add a file named `.env` to the root of the project.

The structure of the `.env` file is as follows:

```
DOTENV_TEST_MESSAGE=PING
KAFKA_STREAM_TEST_TOPIC=<TOPIC_NAME_ON_KAFKA_STREAMS>
KAFKA_STREAM_USER_NAME=<SERVICE_ACCOUNT_CLIENT_ID>
KAFKA_STREAM_PWD=<SERVICE_ACCOUNT_CLIENT_SECRET>
KAFKA_STREAM_BOOTSTRAP_SERVER=<KAFKA_STREAMS_BOOTSTRAP_SERVER_URL>
```

**WHERE**

* `<TOPIC_NAME_ON_KAFKA_STREAMS>` is the name of topic where messages will be produced to and consumed from. The topic must be created within in Red Hat OpenShift Kafka Streams.
* `<SERVICE_ACCOUNT_CLIENT_ID>` is the Client ID created when you created the OpenShift Service Account bound to the stream.
* `<SERVICE_ACCOUNT_CLIENT_SECRET>` is the Client Secret created when you created the OpenShift Service Account bound to the stream.
* `<KAFKA_STREAMS_BOOTSTRAP_SERVER_URL>` is the URL of the Kafka Bootstrap which is created when you create the instance of OpenShift Kafka Streams.
* 
You will substitute the values between the `<...>` brackets with values particular to your instance of OpenShift Kafka Streams.

## Building the code
TO BE PROVIDED

## Running the tests
TO BE PROVIDED
