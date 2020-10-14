package com.guidetap.stark.service

import com.guidetap.stark.client.ShopifyClient
import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.client.model.CustomerResponse
import com.guidetap.stark.client.model.ShopifyGetRequest
import com.guidetap.stark.client.model.PaginatedShopifyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ShopifyCustomerService(
  private val shopifyClient: ShopifyClient,
  private val managementAPIService: ManagementAPIService,
  private val urlParser: UrlParser
) {

  companion object {
    val SHOPIFY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS")
  }

  private val log = LoggerFactory.getLogger(javaClass)

  suspend fun getCustomers(
    userId: String,
    pageInfo: String? = null,
    lastSyncDate: LocalDateTime? = null
  ): PaginatedShopifyResponse<CustomerResponse> =
    managementAPIService.getUserData(userId)
      .let {
        shopifyClient.getCustomers(
          ShopifyGetRequest(
            domain = it.domain,
            token = it.identities.first().accessToken,
            pageInfo = pageInfo,
            updatedAtMin = lastSyncDate?.format(SHOPIFY_FORMATTER)
          )
        )
      }

  fun getAllCustomers(userId: String, lastSyncDate: LocalDateTime?): Flow<Customer> =
    flow {
      log.info("process='getAllCustomers' message='has been started' userId='$userId'")
      var pageInfo: String? = null
      while (true) {
        log.info("process='getAllCustomers' message='new request prepared' userId='$userId' pageInfo='$pageInfo'")
        val customerSince = getCustomers(userId, pageInfo, lastSyncDate)
        customerSince.shopifyResponse.customers.forEach {
          emit(it)
        }
        if (customerSince.pageInfoUrl == null) {
          break
        }
        pageInfo = urlParser.extractParamUrl(customerSince.pageInfoUrl, "page_info")
      }
    }
}
