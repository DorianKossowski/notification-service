package com.codibly_doko.notificationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "spring.rabbitmq")
@ConstructorBinding
data class RabbitMQProperties(
    val exchangeName: String,
    val queueName: String,
    val queueBindingKey: String
)
