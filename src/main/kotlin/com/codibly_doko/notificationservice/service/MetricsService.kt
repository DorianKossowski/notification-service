package com.codibly_doko.notificationservice.service

import com.codibly_doko.notificationservice.config.MetricsConfiguration
import io.micrometer.core.annotation.Counted
import org.springframework.stereotype.Service

@Service
class MetricsService {
    @Counted(MetricsConfiguration.SENT_MAILS)
    fun incrementSentMails() {
    }
}
