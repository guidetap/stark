package com.guidetap.stark.controller

import com.guidetap.stark.controller.model.AttributesRequestBody
import com.guidetap.stark.service.BrandAttributesService
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brand/api/v1/attributes")
class BrandAttributesController(
  private val brandAttributesService: BrandAttributesService
) {

  @GetMapping
  suspend fun findAll(@AuthenticationPrincipal authentication: Authentication) =
    brandAttributesService.findAll(authentication.name)

  @PutMapping
  suspend fun save(
    @AuthenticationPrincipal authentication: Authentication,
    @RequestBody attributesRequestBody: AttributesRequestBody
  ) =
    brandAttributesService.saveAttributes(authentication.name, attributesRequestBody)
}
