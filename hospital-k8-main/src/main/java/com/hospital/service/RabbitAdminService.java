package com.hospital.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private final AmqpAdmin amqpAdmin;

    // Inyección por constructor (Buena práctica exigida en la rúbrica)
    public RabbitAdminService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void createQueue(String queueName) {
        // durable = true: la cola sobrevive si RabbitMQ se reinicia
        amqpAdmin.declareQueue(new Queue(queueName, true));
    }

    public void deleteQueue(String queueName) {
        amqpAdmin.deleteQueue(queueName);
    }
}