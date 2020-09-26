package com.guidetap.stark.controller.handlers

import io.ktor.client.features.*
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

  private val log = LoggerFactory.getLogger(javaClass)

  @ExceptionHandler(ClientRequestException::class)
  fun handleClientError(ex: ClientRequestException): ResponseEntity<ErrorEntity> =
    ex.let {
      log.warn("Exception during client request message=${ex.message}", ex)
      ResponseEntity.badRequest()
        .body(ErrorEntity(ex.message ?: "Unrecognized error message"))
    }
}

