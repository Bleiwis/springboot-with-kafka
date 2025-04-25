package com.example.api_reactiva_kafka.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer for receiving messages.
 */
@Service
public class KafkaConsumer {

    /**
     * Listen to messages from the Kafka topic.
     * @param message Received message.
     */
    @KafkaListener(topics = "example-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
