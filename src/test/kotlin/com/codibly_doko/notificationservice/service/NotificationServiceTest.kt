package com.codibly_doko.notificationservice.service

import com.codibly_doko.notificationservice.message.MessageProvider
import com.codibly_doko.notificationservice.model.NotificationInfoDto
import com.codibly_doko.notificationservice.model.NotificationType
import com.codibly_doko.notificationservice.sender.NotificationSender
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class NotificationServiceTest {
    companion object {
        private val NOTIFICATION_TYPE = NotificationType.MAIL
        private const val TEMPLATE_NAME = "template"
        private const val TO_MAIL = "toMail"
        private const val SUBJECT = "subject"
        private val PARAMS = mapOf<String, String>()
        private val DTO = NotificationInfoDto(NOTIFICATION_TYPE, TEMPLATE_NAME, TO_MAIL, SUBJECT, PARAMS)
    }

    private val messageProvider = mock(MessageProvider::class.java)
    private val notificationSender = mock(NotificationSender::class.java)
    private val notificationService = NotificationService(messageProvider, listOf(notificationSender))

    @Test
    fun shouldHandle() {
        // given
        val message = "message"
        `when`(messageProvider.provide(TEMPLATE_NAME, PARAMS)).thenReturn(message)
        `when`(notificationSender.canSend(NOTIFICATION_TYPE)).thenReturn(true)

        // when
        notificationService.handleNotification(DTO)

        // then
        verify(notificationSender).send(TO_MAIL, SUBJECT, message)
    }

    @Test
    fun shouldThrowWhenNotSupportedSender() {
        `when`(notificationSender.canSend(NOTIFICATION_TYPE)).thenReturn(false)

        assertThatThrownBy { notificationService.handleNotification(DTO) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Cannot send notification of type MAIL")
    }
}