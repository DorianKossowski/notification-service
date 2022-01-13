package com.codibly_doko.notificationservice.message

interface MessageProvider {
    fun provide(templateName: String, params: Map<String, String>): String
}
