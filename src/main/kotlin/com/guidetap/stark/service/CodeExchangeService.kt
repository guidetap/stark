package com.guidetap.stark.service

import com.guidetap.stark.client.AuthenticationClient
import org.springframework.stereotype.Service

@Service
class CodeExchangeService(
    private val authenticationClient: AuthenticationClient
) {

  suspend fun exchangeCode(code: String) = authenticationClient.getToken(code)

}

