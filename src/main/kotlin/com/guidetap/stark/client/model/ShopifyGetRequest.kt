package com.guidetap.stark.client.model

data class ShopifyGetRequest(
  val domain: String,
  val token: String,
  val pageInfo: String? = null,
  val createdAtMin: String? = null,
  val updatedAtMin: String? = null,
)