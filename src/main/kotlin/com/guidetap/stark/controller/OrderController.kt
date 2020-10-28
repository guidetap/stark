package com.guidetap.stark.controller

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.client.model.Order
import com.guidetap.stark.converter.Converter
import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.repository.model.OrderEntity
import com.guidetap.stark.service.ShopifyCustomerService
import com.guidetap.stark.service.ShopifyOrderService
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
@RequestMapping("/brand/api/v1/order")
class OrderController(
  private val shopifyOrderService: ShopifyOrderService,
  private val shopifySyncService: ShopifySyncService
) {

  @GetMapping
  fun getAllOrders(@AuthenticationPrincipal authentication: Authentication): Flow<Order> =
    shopifyOrderService.getAllOrders(authentication.name, null)

  @PutMapping("sync")
  suspend fun sync(@AuthenticationPrincipal authentication: Authentication): Flow<OrderEntity> =
    shopifySyncService.syncShopifyOrdersFor(authentication.name)
}