package com.guidetap.stark.controller

import com.guidetap.stark.client.model.TokenResponse
import com.guidetap.stark.controller.model.CodeExchangeRequestBody
import com.guidetap.stark.service.CompleteSignInService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/login/code")
class CodeExchangeController(
    private val completeSignInService: CompleteSignInService
) {

  @PostMapping("exchange")
  suspend fun exchangeCode(@RequestBody @Valid body: CodeExchangeRequestBody): TokenResponse =
      completeSignInService.completeSignIn(body.code)
}