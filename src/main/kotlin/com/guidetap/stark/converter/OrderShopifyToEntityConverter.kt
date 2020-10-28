package com.guidetap.stark.converter

import com.guidetap.stark.client.model.Order
import com.guidetap.stark.repository.model.OrderEntity
import org.springframework.stereotype.Component

@Component
class OrderShopifyToEntityConverter : Converter<Order, OrderEntity> {
  override fun convert(source: Order): OrderEntity =
    OrderEntity(
      shopifyId = source.shopifyId,
      createdAt = source.createdAt,
      updatedAt = source.updatedAt,
      details = source.details
    )
}