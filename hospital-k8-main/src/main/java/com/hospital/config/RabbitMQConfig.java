package com.hospital.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 1. Leer los nombres centralizados del application.yml
    @Value("${rabbitmq.exchanges.hospital}")
    private String exchangeName;

    @Value("${rabbitmq.queues.notifications}")
    private String notificationsQueue;

    @Value("${rabbitmq.queues.notifications-dlq}")
    private String notificationsDlq;

    @Value("${rabbitmq.routing-keys.notifications}")
    private String notificationsRoutingKey;

    @Value("${rabbitmq.routing-keys.notifications-dlq}")
    private String dlqRoutingKey;

    // 2. Definir el Exchange principal
    @Bean
    public DirectExchange hospitalExchange() {
        return new DirectExchange(exchangeName);
    }

    // 3. Definir la Dead Letter Queue (DLQ)
    @Bean
    public Queue notificationsDlq() {
        return QueueBuilder.durable(notificationsDlq).build();
    }

    // 4. Definir la cola principal y vincularla a la DLQ
    @Bean
    public Queue notificationsQueue() {
        return QueueBuilder.durable(notificationsQueue)
                .withArgument("x-dead-letter-exchange", exchangeName)
                .withArgument("x-dead-letter-routing-key", dlqRoutingKey)
                .build();
    }

    // 5. Crear los Bindings (Enlazar colas con el exchange mediante routing keys)
    @Bean
    public Binding bindNotificationsDlq() {
        return BindingBuilder.bind(notificationsDlq())
                .to(hospitalExchange())
                .with(dlqRoutingKey);
    }

    @Bean
    public Binding bindNotificationsQueue() {
        return BindingBuilder.bind(notificationsQueue())
                .to(hospitalExchange())
                .with(notificationsRoutingKey);
    }
}