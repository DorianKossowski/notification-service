package com.codibly_doko.notificationservice.sender

import com.codibly_doko.notificationservice.service.MetricsService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.verify
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import javax.mail.internet.MimeMessage

internal class MailNotificationSenderTest {
    companion object {
        private const val FROM = "from@mail.com"
        private const val TO = "to@mail.com"
        private const val SUBJECT = "subject"
        private const val MESSAGE = "message"
    }

    private val javaMailSender = mock(JavaMailSender::class.java, RETURNS_DEEP_STUBS)
    private val metricsService = mock(MetricsService::class.java)
    private val mailNotificationSender = spy(MailNotificationSender(javaMailSender, FROM, metricsService))

    @Test
    internal fun shouldSendMail() {
        // given
        val mimeMessage = mock(MimeMessage::class.java)
        val mimeMessageHelper = mock(MimeMessageHelper::class.java)
        `when`(javaMailSender.createMimeMessage()).thenReturn(mimeMessage)
        doReturn(mimeMessageHelper).`when`(mailNotificationSender).getMimeMessageHelper(mimeMessage)

        // when
        mailNotificationSender.send(TO, SUBJECT, MESSAGE)

        // then
        verify(javaMailSender).send(mimeMessage)
        verify(metricsService).incrementSentMails()
        verify(mimeMessageHelper).setFrom(FROM)
        verify(mimeMessageHelper).setTo(TO)
        verify(mimeMessageHelper).setSubject(SUBJECT)
        verify(mimeMessageHelper).setText(MESSAGE, true)
    }
}