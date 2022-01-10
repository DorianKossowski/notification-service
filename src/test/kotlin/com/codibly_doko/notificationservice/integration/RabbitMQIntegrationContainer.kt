package com.codibly_doko.notificationservice.integration;

import com.codibly_doko.notificationservice.config.RabbitMQProperties
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [RabbitMQIntegrationContainer.Initializer::class])
abstract class RabbitMQIntegrationContainer {

    companion object {
        const val TEST_QUEUE_NAME = "test.queue"
        const val TEST_QUEUE_ROUTING_KEY = "test.notification.*";
        val rabbit: RabbitMQContainer = RabbitMQContainer("rabbitmq:3.8.18-management-alpine")
            .withExposedPorts(5672, 15672)

        init {
            rabbit.start()
        }
    }

    @Autowired
    lateinit var rabbitMQProperties: RabbitMQProperties

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    lateinit var rabbitAdmin: RabbitAdmin

    @TestConfiguration
    class RabbitMQTestConfig {

        @Bean
        fun testQueue() = Queue(TEST_QUEUE_NAME)

        @Bean
        fun testBinding(queue: Queue, exchange: DirectExchange): Binding =
            BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(TEST_QUEUE_ROUTING_KEY);
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            val values = TestPropertyValues.of(
                "spring.rabbitmq.host=" + rabbit.containerIpAddress,
                "spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
            )
            values.applyTo(configurableApplicationContext);
        }
    }
}
