package com.codibly_doko.notificationservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class NotificationInfoDto(
    @JsonProperty("type") val notificationType: NotificationType,
    val templateName: String,
    val toMail: String,
    val subject: String,
    val params: Map<String, String>
) : Serializable
