package com.guidetap.stark.client.model

class PaginatedShopifyResponse<T>(
  val shopifyResponse: T,
  val pageInfoUrl: String?
)