package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CustomerResponse(
    val customers: List<Customer>
)

data class Customer(
    val id: Long,
    val email: String,
    @JsonProperty("accepts_marketing")
    val acceptsMarketing: Boolean,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    @JsonProperty("orders_count")
    val ordersCount: Long,
    val state: String,
    @JsonProperty("total_spent")
    val totalSpent: String,
    @JsonProperty("last_order_id")
    val lastOrderId: Long,
    val note: String?,
    @JsonProperty("verified_email")
    val verifiedEmail: Boolean,
    @JsonProperty("multipass_identifier")
    val multipassIdentifier: String?,
    @JsonProperty("tax_exempt")
    val taxExempt: Boolean,
    val phone: String,
    val tags: String,
    @JsonProperty("last_order_name")
    val lastOrderName: String,
    val currency: String,
    val addresses: List<Address>,
    @JsonProperty("accepts_marketing_updated_at")
    val acceptsMarketingUpdatedAt: LocalDateTime,
    @JsonProperty("marketing_opt_in_level")
    val marketingOptInLevel: String?,
    @JsonProperty("tax_exemptions")
    val taxExemptions: List<String>,
    @JsonProperty("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @JsonProperty("default_address")
    val defaultAddress: Address,
)

data class Address(
    val id: Long,
    @JsonProperty("customer_id")
    val customerId: Long,
    @JsonProperty("first_name")
    val firstName: String?,
    @JsonProperty("last_name")
    val lastName: String?,
    val company: String?,
    val address1: String,
    val address2: String,
    val city: String,
    val province: String,
    val country: String,
    val zip: String,
    val phone: String,
    val name: String,
    @JsonProperty("province_code")
    val provinceCode: String,
    @JsonProperty("country_code")
    val countryCode: String,
    @JsonProperty("country_name")
    val countryName: String,
    val default: Boolean,
)