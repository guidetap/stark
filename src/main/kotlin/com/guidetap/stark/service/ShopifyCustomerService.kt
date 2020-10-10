package com.guidetap.stark.service

import com.guidetap.stark.client.ShopifyClient
import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.client.model.GetCustomerRequest
import com.guidetap.stark.client.model.PaginatedCustomerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShopifyCustomerService(
  private val shopifyClient: ShopifyClient,
  private val managementAPIService: ManagementAPIService,
  private val urlParser: UrlParser
) {

  private val log = LoggerFactory.getLogger(javaClass)

  suspend fun getCustomers(userId: String, pageInfo: String? = null): PaginatedCustomerResponse =
    managementAPIService.getUserData(userId)
      .let {
        shopifyClient.getCustomers(
          GetCustomerRequest(
            domain = it.domain,
            token = it.identities.first().accessToken,
            pageInfo = pageInfo
          )
        )
      }

  fun getAllCustomers(userId: String): Flow<Customer> =
    flow {
      log.info("process='getAllCustomers' message='has been started' userId='$userId'")
      var pageInfo: String? = null
      while (true) {
        log.info("process='getAllCustomers' message='new request prepared' userId='$userId' pageInfo='$pageInfo'")
        val customerSince = getCustomers(userId, pageInfo)
        if (customerSince.pageInfoUrl == null) {
          break
        }
        pageInfo = urlParser.extractParamUrl(customerSince.pageInfoUrl, "page_info")
        customerSince.customerResponse.customers.forEach {
          emit(it)
        }
      }
    }
}
