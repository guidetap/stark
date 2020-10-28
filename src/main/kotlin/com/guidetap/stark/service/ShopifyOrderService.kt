package com.guidetap.stark.service

import com.guidetap.stark.client.ShopifyClient
import com.guidetap.stark.client.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class ShopifyOrderService(
  private val shopifyClient: ShopifyClient,
  private val managementAPIService: ManagementAPIService,
  private val urlParser: UrlParser
) {

  companion object {
    val SHOPIFY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS")
  }

  private val log = LoggerFactory.getLogger(javaClass)

  suspend fun getOrders(
    userId: String,
    pageInfo: String? = null,
    lastSyncDate: ZonedDateTime? = null
  ): PaginatedShopifyResponse<OrderResponse> =
    managementAPIService.getUserData(userId)
      .let {
        shopifyClient.getOrders(
          ShopifyGetRequest(
            domain = it.domain,
            token = it.identities.first().accessToken,
            pageInfo = pageInfo,
            updatedAtMin = lastSyncDate?.format(SHOPIFY_FORMATTER)
          )
        )
      }

  fun getAllOrders(userId: String, lastSyncDate: ZonedDateTime?): Flow<Order> =
    flow {
      log.info("process='getAllCustomers' message='has been started' userId='$userId'")
      var pageInfo: String? = null
      while (true) {
        log.info("process='getAllCustomers' message='new request prepared' userId='$userId' pageInfo='$pageInfo'")
        val ordersSince = getOrders(userId, pageInfo, lastSyncDate)
        ordersSince.shopifyResponse.orders.forEach {
          emit(it)
        }
        if (ordersSince.pageInfoUrl == null) {
          break
        }
        pageInfo = urlParser.extractParamUrl(ordersSince.pageInfoUrl, "page_info")
      }
    }
}
