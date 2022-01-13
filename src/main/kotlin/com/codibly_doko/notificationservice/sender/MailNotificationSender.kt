package com.codibly_doko.notificationservice.sender

import com.codibly_doko.notificationservice.model.NotificationType
import org.springframework.stereotype.Component

@Component
class MailNotificationSender : NotificationSender {

    override fun send(to: String, subject: String, message: String) {
        // TODO
    }

    override fun canSend(notificationType: NotificationType) = NotificationType.MAIL == notificationType
}
