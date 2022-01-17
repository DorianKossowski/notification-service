package com.codibly_doko.notificationservice.integration

import com.codibly_doko.notificationservice.model.NotificationInfoDto
import com.codibly_doko.notificationservice.model.NotificationType
import com.codibly_doko.notificationservice.sender.MailNotificationSender
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.any
import org.mockito.kotlin.timeout
import org.mockito.kotlin.verify
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.mail.javamail.JavaMailSender
import javax.mail.internet.MimeMessage

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationIntegrationTest : RabbitMQIntegrationContainer() {
    companion object {
        const val TO_MAIL = "to@mail.com"
        const val SUBJECT = "Some subject"
    }

    @SpyBean
    private lateinit var javaMailSender: JavaMailSender

    @SpyBean
    private lateinit var mailNotificationSender: MailNotificationSender

    @AfterEach
    fun clearQueue() {
        rabbitAdmin.purgeQueue(TEST_QUEUE_NAME)
    }

    @Test
    fun shouldHandleNotification() {
        //given
        val dto = NotificationInfoDto(
            notificationType = NotificationType.MAIL,
            templateName = "TestTemplate",
            toMail = TO_MAIL,
            subject = SUBJECT,
            params = mapOf(Pair("testParam", "TestValue"))
        )
        val expected = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>TestTemplate</title>
            </head>
            <body>
            <p>TestValue</p>
            </body>
            </html>
        """.trimIndent()

        //when
        rabbitTemplate.convertAndSend(rabbitMQProperties.exchangeName, TEST_QUEUE_ROUTING_KEY, dto)

        //then
        verify(mailNotificationSender, timeout(6000)).send(TO_MAIL, SUBJECT, expected)
        verify(javaMailSender, timeout(6000)).send(any() as MimeMessage)
    }
}