package com.codibly_doko.notificationservice.queue

import com.codibly_doko.notificationservice.model.NotificationInfoDto
import com.codibly_doko.notificationservice.service.NotificationService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class NotificationRabbitListener(private val notificationService: NotificationService) {

    @RabbitListener(queues = ["\${spring.rabbitmq.queue-name}"])
    fun listenNotification(notificationInfoDto: NotificationInfoDto) {
        notificationService.handleNotification(notificationInfoDto)
    }
}
