package com.guidetap.stark.controller

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.converter.Converter
import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.service.ShopifyCustomerService
import com.guidetap.stark.service.ShopifySyncService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customer")
class CustomerController(
  private val shopifyCustomerService: ShopifyCustomerService,
  private val customerConverter: Converter<Customer, CustomerEntity>,
  private val shopifySyncService: ShopifySyncService
) {

  @GetMapping
  fun getAllCustomers(@AuthenticationPrincipal authentication: Authentication): Flow<CustomerEntity> =
    shopifyCustomerService.getAllCustomers(authentication.name)
      .map { customerConverter.convert(it) }

  @PutMapping("sync")
  fun syncCustomer(@AuthenticationPrincipal authentication: Authentication): Flow<CustomerEntity> =
    shopifySyncService.syncShopifyCustomersFor(authentication.name)

}