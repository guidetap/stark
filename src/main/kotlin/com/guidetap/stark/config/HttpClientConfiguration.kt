package com.guidetap.stark.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfiguration {

  companion object {
    const val AUTH0_HOST = "guidetap.us.auth0.com"
  }

  @Bean
  fun auth0HttpClient() = HttpClient(CIO) {
    engine {
      https {
        serverName = AUTH0_HOST
      }
    }
    installBasicHttpClient()
  }

  @Bean
  fun shopifyHttpClient() = HttpClient(CIO) {
    installBasicHttpClient()
  }

  private fun HttpClientConfig<CIOEngineConfig>.installBasicHttpClient() {
    install(JsonFeature) {
      serializer = JacksonSerializer(
          ObjectMapper()
              .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
              .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
              .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
              .registerModule(KotlinModule())
              .also { it.findAndRegisterModules() }
      )
    }
    install(Logging) {
      logger = Logger.DEFAULT
      level = LogLevel.HEADERS
    }
  }
}