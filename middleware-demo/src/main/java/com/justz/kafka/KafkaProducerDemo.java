package com.justz.kafka;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author zhangcm
 */
public class KafkaProducerDemo {

    public static void main(String[] args) {
        String topic = "my-test-topic";

        ZkClient zkClient = new ZkClient("localhost:2181/kafka", 10000, 10000, ZKStringSerializer$.MODULE$);
        if (!AdminUtils.topicExists(zkClient, topic)) {
            // zkClient topic partitions replication-factor topicConfig
            AdminUtils.createTopic(zkClient, topic, 1, 1, new Properties());
        }


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            producer.send(new ProducerRecord<>(topic, Integer.toString(i), Integer.toString(i)));
        }

        producer.close();
    }
}
