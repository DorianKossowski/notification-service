package com.codibly_doko.notificationservice.sender

import com.codibly_doko.notificationservice.model.NotificationType
import com.codibly_doko.notificationservice.service.MetricsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import javax.mail.internet.MimeMessage


@Component
class MailNotificationSender(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.from}") private val from: String,
    private val metricsService: MetricsService
) : NotificationSender {

    override fun send(to: String, subject: String, message: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        with(getMimeMessageHelper(mimeMessage)) {
            setFrom(from)
            setTo(to)
            setSubject(subject)
            setText(message, true)
        }
        mailSender.send(mimeMessage)
        metricsService.incrementSentMails()
    }

    internal fun getMimeMessageHelper(mimeMessage: MimeMessage) = MimeMessageHelper(mimeMessage, true)

    override fun canSend(notificationType: NotificationType) = NotificationType.MAIL == notificationType
}
