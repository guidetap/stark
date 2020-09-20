package com.guidetap.stark.converter

import com.guidetap.stark.client.model.Customer
import com.guidetap.stark.repository.model.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerShopifyToEntityConverter : Converter<Customer, CustomerEntity> {
  override fun convert(source: Customer): CustomerEntity =
      CustomerEntity(
          shopifyId = source.id,
          email = source.email,
          acceptsMarketing = source.acceptsMarketing,
          createdAt = source.createdAt,
          updatedAt = source.updatedAt,
          firstName = source.firstName,
          lastName = source.lastName,
          ordersCount = source.ordersCount,
          state = source.state,
          totalSpent = source.totalSpent,
          lastOrderId = source.lastOrderId,
          note = source.note,
          verifiedEmail = source.verifiedEmail,
          multipassIdentifier = source.multipassIdentifier,
          taxExempt = source.taxExempt,
          phone = source.phone,
          tags = source.tags,
          lastOrderName = source.lastOrderName,
          currency = source.currency,
          addresses = source.addresses,
          acceptsMarketingUpdatedAt = source.acceptsMarketingUpdatedAt,
          marketingOptInLevel = source.marketingOptInLevel,
          taxExemptions = source.taxExemptions,
          adminGraphqlApiId = source.adminGraphqlApiId,
          defaultAddress = source.defaultAddress,
      )
}