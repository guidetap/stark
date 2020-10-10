package com.guidetap.stark.client.model

data class GetCustomerRequest(
  val domain: String,
  val token: String,
  val pageInfo: String? = null,
  val createdAtMin: String? = null
)