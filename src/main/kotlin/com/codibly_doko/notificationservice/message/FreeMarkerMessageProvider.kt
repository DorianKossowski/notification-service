package com.codibly_doko.notificationservice.message

import freemarker.template.Template
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer


@Component
class FreeMarkerMessageProvider(private val freemarkerConfigurer: FreeMarkerConfigurer) : MessageProvider {

    override fun provide(templateName: String, params: Map<String, String>): String {
        val freemarkerTemplate: Template = freemarkerConfigurer.configuration.getTemplate("$templateName.ftl")
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, params)
    }
}
