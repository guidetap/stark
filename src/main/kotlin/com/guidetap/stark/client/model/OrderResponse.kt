package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderResponse(
  val customers: List<Order>,
)

data class Order(
  @JsonProperty("app_id")
  val appId: Long,

  @get:JsonAnyGetter
  @JsonAnySetter
  val details: Map<String, Any> = emptyMap()
)