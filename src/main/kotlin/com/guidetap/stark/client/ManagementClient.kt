package com.guidetap.stark.client

import com.guidetap.stark.client.model.ManagementTokenRequestBody
import com.guidetap.stark.client.model.TokenResponse
import com.guidetap.stark.client.model.UserResponse
import com.guidetap.stark.config.HttpClientConfiguration
import com.guidetap.stark.properties.Auth0Properties
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.stereotype.Component

@Component
class ManagementClient(
  private val auth0HttpClient: HttpClient,
  private val auth0Properties: Auth0Properties
) {

  suspend fun getBrandManagementToken(): TokenResponse =
    auth0HttpClient.post(urlString = "/oauth/token") {
      url {
        host = HttpClientConfiguration.AUTH0_HOST
        protocol = URLProtocol.HTTPS
      }
      header(HttpHeaders.ContentType, ContentType.Application.Json)
      body = ManagementTokenRequestBody(
        clientId = auth0Properties.brand.clientId,
        clientSecret = auth0Properties.brand.clientSecret,
        audience = auth0Properties.brand.audience,
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