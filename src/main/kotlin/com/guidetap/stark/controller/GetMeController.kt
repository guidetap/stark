package com.guidetap.stark.controller

import com.guidetap.stark.service.ManagementAPIService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me")
class GetMeController(
    private val managementAPIService: ManagementAPIService
) {

  @GetMapping
  suspend fun getMe() = ReactiveSecurityContextHolder.getContext()
      .map { it.authentication }
      .awaitFirst()

  @GetMapping("/auth0")
  suspend fun getFromAuth0() = ReactiveSecurityContextHolder.getContext()
      .awaitFirst()
      .authentication
      .takeIf { it is JwtAuthenticationToken }
      ?.let { it as JwtAuthenticationToken }
      ?.name
      ?.let { managementAPIService.getUserData(it) }

}