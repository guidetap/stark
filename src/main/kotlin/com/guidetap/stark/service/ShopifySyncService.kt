package com.guidetap.stark.service

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.converter.Converter
import com.guidetap.stark.repository.model.CustomerEntity
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class ShopifySyncService(
    private val shopifyCustomerService: ShopifyCustomerService,
    private val customerConverter: Converter<Customer, CustomerEntity>,
    private val customerEntityService: CustomerEntityService
) {

  fun syncShopifyCustomersFor(userId: String) =
      shopifyCustomerService.getAllCustomers(userId)
          .map { customerConverter.convert(it) }
          .map { customerEntityService.insert(it) }
          .map { it.insertedId }
}