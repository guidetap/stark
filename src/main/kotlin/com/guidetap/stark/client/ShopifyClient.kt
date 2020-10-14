package com.guidetap.stark.client

import com.guidetap.stark.CommonConstants.Companion.SHOPIFY_TOKEN_HEADER
import com.guidetap.stark.client.model.*
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

  suspend fun getCustomers(shopifyGetRequest: ShopifyGetRequest): PaginatedShopifyResponse<CustomerResponse> =
    shopifyHttpClient.get<HttpResponse>("/admin/api/2020-07/customers.json") {
      url {
        host = shopifyGetRequest.domain
        protocol = URLProtocol.HTTPS
      }
      header(SHOPIFY_TOKEN_HEADER, shopifyGetRequest.token)
      shopifyGetRequest.pageInfo
        ?.let { parameter("page_info", it) }

      setIfPageInfo(shopifyGetRequest.pageInfo) {
        shopifyGetRequest.createdAtMin?.let { parameter("created_at_min", it) }
      }
      setIfPageInfo(shopifyGetRequest.pageInfo) {
        shopifyGetRequest.updatedAtMin?.let { parameter("updated_at_min", it) }
      }

      parameter("limit", 250)
    }
      .let {
        PaginatedShopifyResponse(
          it.receive(),
          it.headers["Link"]?.let { link -> pageInfoExtractor.extractPageInfo(link) }
        )
      }

  suspend fun getOrders(shopifyGetRequest: ShopifyGetRequest): PaginatedShopifyResponse<OrderResponse> =
    shopifyHttpClient.get<HttpResponse>("/admin/api/2020-10/orders.json?status=any") {
      url {
        host = shopifyGetRequest.domain
        protocol = URLProtocol.HTTPS
      }
      header(SHOPIFY_TOKEN_HEADER, shopifyGetRequest.token)
      shopifyGetRequest.pageInfo
        ?.let { parameter("page_info", it) }
      setIfPageInfo(shopifyGetRequest.pageInfo) {
        shopifyGetRequest.createdAtMin?.let { parameter("created_at_min", it) }
      }
      setIfPageInfo(shopifyGetRequest.pageInfo) {
        shopifyGetRequest.updatedAtMin?.let { parameter("updated_at_min", it) }
      }
      parameter("limit", 250)
    }
      .let {
        PaginatedShopifyResponse(
          it.receive(),
          it.headers["Link"]?.let { link -> pageInfoExtractor.extractPageInfo(link) }
        )
      }

  fun HttpRequestBuilder.setIfPageInfo(pageInfo: String?, apply: HttpRequestBuilder.() -> Unit): Any =
    pageInfo ?: apply()

}