package com.guidetap.stark.controller

import com.guidetap.stark.controller.model.CodeExchangeRequestBody
import com.guidetap.stark.service.CodeExchangeService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/login/code")
class CodeExchangeController(
    private val codeExchangeService: CodeExchangeService
) {

  @PostMapping("exchange")
  suspend fun exchangeCode(@RequestBody @Valid body: CodeExchangeRequestBody) =
      codeExchangeService.exchangeCode(body.code)
}