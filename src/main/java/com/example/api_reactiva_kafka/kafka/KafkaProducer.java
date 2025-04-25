package com.example.api_reactiva_kafka.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer for sending messages.
 */
@Service
public class KafkaProducer {

    private static final String TOPIC = "example-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Send a message to the Kafka topic.
     * @param message Message to send.
     */
    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
