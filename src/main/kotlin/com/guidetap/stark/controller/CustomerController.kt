package com.guidetap.stark.controller

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.service.ShopifyCustomerService
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customer")
class CustomerController(
    private val shopifyCustomerService: ShopifyCustomerService
) {

  @GetMapping
  suspend fun getSome(@AuthenticationPrincipal authentication: Authentication): List<Customer> =
      shopifyCustomerService.getCustomers(authentication.name)

}