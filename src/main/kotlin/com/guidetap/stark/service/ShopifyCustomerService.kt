package com.guidetap.stark.service

import com.guidetap.stark.client.ShopifyClient
import com.guidetap.stark.client.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShopifyCustomerService(
    private val shopifyClient: ShopifyClient,
    private val managementAPIService: ManagementAPIService
) {

  private val log = LoggerFactory.getLogger(javaClass)

  suspend fun getCustomers(userId: String, sinceId: Long? = null): List<Customer> =
      managementAPIService.getUserData(userId)
          .let {
            shopifyClient.getCustomers(
                domain = it.domain,
                token = it.identities.first().accessToken,
                sinceId = sinceId
            )
          }
          .customers

  fun getAllCustomers(userId: String): Flow<Customer> =
      flow {
        log.info("process='getAllCustomers' message='has been started' userId='$userId'")
        var sinceId: Long? = null
        while (true) {
          log.info("process='getAllCustomers' message='new request prepared' userId='$userId' sinceId='$sinceId'")
          val customerSince = getCustomers(userId, sinceId)
          if (customerSince.isEmpty()) {
            break
          }
          sinceId = customerSince.lastOrNull()?.id
          customerSince.forEach {
            emit(it)
          }
        }
      }
}
