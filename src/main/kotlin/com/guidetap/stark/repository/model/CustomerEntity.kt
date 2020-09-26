package com.guidetap.stark.repository.model

import com.guidetap.stark.client.model.Address
import java.time.ZonedDateTime

data class CustomerEntity(
    val shopifyId: Long,
    val email: String?,
    val acceptsMarketing: Boolean,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val firstName: String?,
    val lastName: String?,
    val ordersCount: Long,
    val state: String,
    val totalSpent: String,
    val lastOrderId: Long,
    val note: String?,
    val verifiedEmail: Boolean,
    val multipassIdentifier: String?,
    val taxExempt: Boolean,
    val phone: String?,
    val tags: String,
    val lastOrderName: String?,
    val currency: String,
    val addresses: List<Address>,
    val acceptsMarketingUpdatedAt: ZonedDateTime,
    val marketingOptInLevel: String?,
    val taxExemptions: List<String>,
    val adminGraphqlApiId: String,
    val defaultAddress: Address?,
)