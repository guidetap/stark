package com.guidetap.stark.service

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.client.model.Order
import com.guidetap.stark.converter.Converter
import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.repository.model.OrderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import org.springframework.stereotype.Service

@Service
class ShopifySyncService(
  private val shopifyCustomerService: ShopifyCustomerService,
  private val shopifyOrderService: ShopifyOrderService,
  private val customerConverter: Converter<Customer, CustomerEntity>,
  private val orderConverter: Converter<Order, OrderEntity>,
  private val customerEntityService: CustomerEntityService,
  private val orderEntityService: OrderEntityService,
  private val brandUserEntityService: BrandUserEntityService
) {

  suspend fun syncShopifyCustomersFor(userId: String): Flow<CustomerEntity> =
    brandUserEntityService.findById(userId)
      ?.let { brand ->
        shopifyCustomerService.getAllCustomers(brand.auth0Id, brand.syncData?.lastCustomerDate)
          .map { customerConverter.convert(it) }
          .map { customerEntityService.insert(it) }
          .onCompletion {
            val lastUpdated = customerEntityService.findLastUpdated()
            brandUserEntityService.updateCustomerLastSyncDate(userId, lastUpdated)
          }
      }
      ?: emptyFlow()

  suspend fun syncShopifyOrdersFor(userId: String): Flow<OrderEntity> =
    brandUserEntityService.findById(userId)
      ?.let { brand ->
        shopifyOrderService.getAllOrders(brand.auth0Id, brand.syncData?.lastCustomerDate)
          .map { orderConverter.convert(it) }
          .map { orderEntityService.insert(it) }
          .onCompletion {
            val lastUpdated = orderEntityService.findLastUpdated()
            brandUserEntityService.updateOrderLastSyncDate(userId, lastUpdated)
          }
      }
      ?: emptyFlow()
}