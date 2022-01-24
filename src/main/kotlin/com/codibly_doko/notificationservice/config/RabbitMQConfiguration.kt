package com.codibly_doko.notificationservice.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
@EnableConfigurationProperties(RabbitMQProperties::class)
class RabbitMQConfiguration(private val rabbitMQProperties: RabbitMQProperties) {

    @Bean
    fun exchange() = DirectExchange(rabbitMQProperties.exchangeName)

    @Bean
    fun queue() = Queue(rabbitMQProperties.queueName)

    @Bean
    fun binding(queue: Queue, exchange: DirectExchange): Binding =
        BindingBuilder
            .bind(queue)
            .to(exchange)
            .with(rabbitMQProperties.queueBindingKey)

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate =
        RabbitTemplate(connectionFactory).apply {
            messageConverter = Jackson2JsonMessageConverter(jacksonObjectMapper())
        }

    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory =
        SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory)
            setMessageConverter(Jackson2JsonMessageConverter(jacksonObjectMapper()))
        }
}
