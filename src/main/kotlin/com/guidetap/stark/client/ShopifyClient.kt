package com.guidetap.stark.client

import com.guidetap.stark.CommonConstants.Companion.SHOPIFY_TOKEN_HEADER
import com.guidetap.stark.client.model.GetCustomerRequest
import com.guidetap.stark.client.model.PaginatedCustomerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.springframework.stereotype.Component

@Component
class ShopifyClient(
  private val shopifyHttpClient: HttpClient,
  private val pageInfoExtractor: PageInfoExtractor
) {

  suspend fun getCustomers(getCustomerRequest: GetCustomerRequest): PaginatedCustomerResponse =
    shopifyHttpClient.get<HttpResponse>("/admin/api/2020-07/customers.json") {
      url {
        host = getCustomerRequest.domain
        protocol = URLProtocol.HTTPS
      }
      header(SHOPIFY_TOKEN_HEADER, getCustomerRequest.token)
      getCustomerRequest.pageInfo?.let { parameter("page_info", it) }
      getCustomerRequest.createdAtMin?.let { parameter("created_at_min", it) }
      getCustomerRequest.updatedAtMin?.let { parameter("updated_at_min", it) }
      parameter("limit", 250)
    }
      .let {
        PaginatedCustomerResponse(
          it.receive(),
          it.headers["Link"]?.let { link -> pageInfoExtractor.extractPageInfo(link) }
        )
      }

}