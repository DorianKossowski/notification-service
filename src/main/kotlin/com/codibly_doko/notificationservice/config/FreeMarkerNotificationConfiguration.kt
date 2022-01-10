package com.codibly_doko.notificationservice.config

import freemarker.cache.ClassTemplateLoader
import freemarker.template.Configuration
import freemarker.template.Configuration.VERSION_2_3_27
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer

@org.springframework.context.annotation.Configuration
class FreeMarkerNotificationConfiguration {
    companion object {
        private const val RESOURCE_DIR_NAME = "mail-templates"
    }

    @Bean
    fun freeMarkerConfigurer(): FreeMarkerConfigurer {
        val configuration = Configuration(VERSION_2_3_27)
        configuration.templateLoader = ClassTemplateLoader(javaClass, "/$RESOURCE_DIR_NAME")
        val freeMarkerConfigurer = FreeMarkerConfigurer()
        freeMarkerConfigurer.configuration = configuration
        return freeMarkerConfigurer
    }
}