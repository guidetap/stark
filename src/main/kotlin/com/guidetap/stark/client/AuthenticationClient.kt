package com.guidetap.stark.client

import com.guidetap.stark.client.model.TokenResponse
import com.guidetap.stark.config.HttpClientConfiguration
import com.guidetap.stark.properties.Auth0Properties
import com.guidetap.stark.properties.LoginClientProperties
import com.sun.el.parser.Token
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

  suspend fun getToken(code: String): TokenResponse =
      auth0HttpClient.post(urlString = "/oauth/token") {
        url {
          host = HttpClientConfiguration.AUTH0_HOST
          protocol = URLProtocol.HTTPS
        }
        body = FormDataContent(
            Parameters.build {
              append("code", code)
              append("client_id", auth0Properties.clientId)
              append("client_secret", auth0Properties.clientSecret)
              append("grant_type", "authorization_code")
              append("redirect_uri", loginClientProperties.webRedirectUrl)
            }
        )
      }
}