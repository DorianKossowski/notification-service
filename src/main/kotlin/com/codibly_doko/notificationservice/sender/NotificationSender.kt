package com.codibly_doko.notificationservice.sender

import com.codibly_doko.notificationservice.model.NotificationType

interface NotificationSender {
    fun send(to: String, subject: String, message: String)

    fun canSend(notificationType: NotificationType): Boolean
}
