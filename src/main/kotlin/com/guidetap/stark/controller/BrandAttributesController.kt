package com.guidetap.stark.controller

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.guidetap.stark.controller.model.AttributesRequestBody
import com.guidetap.stark.service.BrandAttributesService
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand/attributes")
class BrandAttributesController(
  private val brandAttributesService: BrandAttributesService
) {

  @PutMapping
  suspend fun save(
    @AuthenticationPrincipal authentication: Authentication,
    @RequestBody attributesRequestBody: AttributesRequestBody
  ) =
    brandAttributesService.saveAttributes(authentication.name, attributesRequestBody)
}
