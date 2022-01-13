package com.codibly_doko.notificationservice.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer

class FreeMarkerMessageProviderTest {

    companion object {
        private const val TEMPLATE_NAME = "NAME"
    }

    private val freeMarkerConfigurer = mock(FreeMarkerConfigurer::class.java, RETURNS_DEEP_STUBS)
    private val freeMarkerMessageProvider = FreeMarkerMessageProvider(freeMarkerConfigurer)

    @Test
    fun shouldAddProperExtension() {
        val captor = ArgumentCaptor.forClass(String::class.java)
        freeMarkerMessageProvider.provide(TEMPLATE_NAME, mapOf())

        verify(freeMarkerConfigurer.configuration).getTemplate(captor.capture())
        assertThat(captor.value).isEqualTo("$TEMPLATE_NAME.ftl")
    }
}