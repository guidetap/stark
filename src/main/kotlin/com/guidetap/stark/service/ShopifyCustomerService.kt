package com.guidetap.stark.service

import com.guidetap.stark.client.ShopifyClient
import com.guidetap.stark.client.model.Customer
import org.springframework.stereotype.Service

@Service
class ShopifyCustomerService(
    private val shopifyClient: ShopifyClient,
    private val managementAPIService: ManagementAPIService
) {

  suspend fun getCustomers(userId: String): List<Customer> =
      managementAPIService.getUserData(userId)
          .let {
            shopifyClient.getCustomers(
                domain = it.domain,
                token = it.identities.first().accessToken,
                sinceId = null
            )
          }
          .customers
}
