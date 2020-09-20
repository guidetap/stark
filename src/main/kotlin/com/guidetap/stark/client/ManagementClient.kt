package com.guidetap.stark.client

import com.guidetap.stark.client.model.ManagementTokenRequestBody
import com.guidetap.stark.client.model.TokenResponse
import com.guidetap.stark.client.model.UserResponse
import com.guidetap.stark.config.HttpClientConfiguration
import com.guidetap.stark.properties.Auth0Properties
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
import org.springframework.stereotype.Component

@Component
class ManagementClient(
    private val auth0HttpClient: HttpClient,
    private val auth0Properties: Auth0Properties
) {

  suspend fun getManagementToken(): TokenResponse =
      auth0HttpClient.post(urlString = "/oauth/token") {
        url {
          host = HttpClientConfiguration.AUTH0_HOST
          protocol = URLProtocol.HTTPS
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = ManagementTokenRequestBody(
            clientId = auth0Properties.clientId,
            clientSecret = auth0Properties.clientSecret,
            audience = auth0Properties.audience,
            grantType = "client_credentials"
        )
      }

  suspend fun getUserById(userId: String, managementToken: String): UserResponse =
      auth0HttpClient.get(urlString = "/api/v2/users/$userId") {
        url {
          host = HttpClientConfiguration.AUTH0_HOST
          protocol = URLProtocol.HTTPS
        }
        header("Authorization", "Bearer $managementToken")
      }
}