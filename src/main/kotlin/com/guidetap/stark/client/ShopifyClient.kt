package com.guidetap.stark.client

import com.guidetap.stark.CommonConstants.Companion.SHOPIFY_TOKEN_HEADER
import com.guidetap.stark.client.model.CustomerResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.stereotype.Component

@Component
class ShopifyClient(
    private val shopifyHttpClient: HttpClient
) {

  suspend fun getCustomers(domain: String, token: String, sinceId: Long?): CustomerResponse =
      shopifyHttpClient.get("/admin/api/2020-07/customers.json") {
        url {
          host = domain
          protocol = URLProtocol.HTTPS
        }
        header(SHOPIFY_TOKEN_HEADER, token)
        sinceId?.let { parameter("since_id", it) }
      }

}