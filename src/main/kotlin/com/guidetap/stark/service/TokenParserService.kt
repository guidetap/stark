package com.guidetap.stark.service

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service

@Service
class TokenParserService(
    private val jwtDecoder: ReactiveJwtDecoder
) {
  suspend fun getIdFromToken(jwtToken: String): String =
      JwtAuthenticationToken(jwtDecoder.decode(jwtToken).awaitFirst()).name
}