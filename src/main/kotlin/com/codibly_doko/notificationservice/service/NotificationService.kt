package com.codibly_doko.notificationservice.service

import com.codibly_doko.notificationservice.message.MessageProvider
import com.codibly_doko.notificationservice.model.NotificationInfoDto
import com.codibly_doko.notificationservice.sender.NotificationSender
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val messageProvider: MessageProvider,
    private val notificationSenders: List<NotificationSender>
) {

    fun handleNotification(notificationInfoDto: NotificationInfoDto) {
        val (type, templateName, toMail, subject, params) = notificationInfoDto
        val message = messageProvider.provide(templateName, params)
        val notificationSender = notificationSenders.stream()
            .filter { it.canSend(type) }
            .findAny()
            .orElseThrow { IllegalArgumentException("Cannot send notification of type $type") }
        notificationSender.send(toMail, subject, message)
    }
}
