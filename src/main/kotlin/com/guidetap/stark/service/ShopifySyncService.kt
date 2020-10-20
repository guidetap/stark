package com.guidetap.stark.service

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.converter.Converter
import com.guidetap.stark.repository.model.CustomerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import org.springframework.stereotype.Service

@Service
class ShopifySyncService(
  private val shopifyCustomerService: ShopifyCustomerService,
  private val customerConverter: Converter<Customer, CustomerEntity>,
  private val customerEntityService: CustomerEntityService,
  private val brandUserEntityService: BrandUserEntityService
) {

  suspend fun syncShopifyCustomersFor(userId: String): Flow<CustomerEntity> =
    brandUserEntityService.findById(userId)
      ?.let { brand ->
        shopifyCustomerService.getAllCustomers(brand.auth0Id, brand.syncData.lastCustomerDate)
          .map { customerConverter.convert(it) }
          .map { customerEntityService.insert(it) }
          .onCompletion {
            val lastUpdated = customerEntityService.findLastUpdated()
            brandUserEntityService.updateLastSyncDate(userId, lastUpdated)
          }
      }
      ?: emptyFlow()
}