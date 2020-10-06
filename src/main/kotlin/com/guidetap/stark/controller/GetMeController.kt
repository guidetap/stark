package com.guidetap.stark.controller

import com.guidetap.stark.service.ManagementAPIService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me")
class GetMeController(
  private val managementAPIService: ManagementAPIService
) {

  @GetMapping
  suspend fun getMe(@AuthenticationPrincipal authentication: Authentication) = authentication

  @GetMapping("/auth0")
  suspend fun getFromAuth0() = ReactiveSecurityContextHolder.getContext()
    .awaitFirst()
    .authentication
    .name
    ?.let { managementAPIService.getUserData(it) }
}