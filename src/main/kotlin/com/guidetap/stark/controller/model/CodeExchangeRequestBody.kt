package com.guidetap.stark.controller.model

import javax.validation.constraints.NotBlank

data class CodeExchangeRequestBody(
  @NotBlank
  val code: String
)