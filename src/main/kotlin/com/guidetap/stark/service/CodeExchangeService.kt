package com.guidetap.stark.service

import com.guidetap.stark.client.Auth0Client
import org.springframework.stereotype.Service

@Service
class CodeExchangeService(
    private val auth0Client: Auth0Client
) {

  suspend fun exchangeCode(code: String) = auth0Client.getToken(code)

}

