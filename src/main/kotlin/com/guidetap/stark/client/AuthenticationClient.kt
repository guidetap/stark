package com.guidetap.stark.client

import com.guidetap.stark.client.model.TokenResponse
import com.guidetap.stark.config.HttpClientConfiguration
import com.guidetap.stark.properties.Auth0Properties
import com.guidetap.stark.properties.LoginClientProperties
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.springframework.stereotype.Component

@Component
class AuthenticationClient(
  private val auth0HttpClient: HttpClient,
  private val auth0Properties: Auth0Properties,
  private val loginClientProperties: LoginClientProperties
) {

  suspend fun getBrandToken(code: String): TokenResponse =
    tokenResponse(code, auth0Properties.brand.clientId, auth0Properties.brand.clientSecret)

  suspend fun getUserToken(code: String): TokenResponse =
    tokenResponse(code, auth0Properties.user.clientId, auth0Properties.user.clientSecret)

  private suspend fun tokenResponse(code: String, clientId: String, clientSecret: String): TokenResponse {
    return auth0HttpClient.post(urlString = "/oauth/token") {
      url {
        host = HttpClientConfiguration.AUTH0_HOST
        protocol = URLProtocol.HTTPS
      }
      body = FormDataContent(
        Parameters.build {
          append("code", code)
          append("client_id", clientId)
          append("client_secret", clientSecret)
          append("grant_type", "authorization_code")
          append("redirect_uri", loginClientProperties.webRedirectUrl)
        }
      )
    }
  }
}