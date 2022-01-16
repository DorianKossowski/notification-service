package com.codibly_doko.notificationservice.sender

import com.codibly_doko.notificationservice.model.NotificationType
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import javax.mail.internet.MimeMessage


@Component
class MailNotificationSender(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.from}") private val from: String
) : NotificationSender {

    override fun send(to: String, subject: String, message: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = getMimeMessageHelper(mimeMessage)
        helper.setFrom(from)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(message, true)
        mailSender.send(mimeMessage)
    }

    internal fun getMimeMessageHelper(mimeMessage: MimeMessage) = MimeMessageHelper(mimeMessage, true)

    override fun canSend(notificationType: NotificationType) = NotificationType.MAIL == notificationType
}
