package com.guidetap.stark.service

import com.guidetap.stark.client.AuthenticationClient
import com.guidetap.stark.client.model.TokenResponse
import org.springframework.stereotype.Service

@Service
class CodeExchangeService(
  private val authenticationClient: AuthenticationClient
) {

  suspend fun exchangeCode(code: String): TokenResponse = authenticationClient.getToken(code)

}

