package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class OrderResponse(
  val orders: List<Order>,
)

data class Order(
  @JsonProperty("id")
  val shopifyId: Long,

  @JsonProperty("created_at")
  val createdAt: ZonedDateTime,
  @JsonProperty("updated_at")
  val updatedAt: ZonedDateTime,

  @get:JsonAnyGetter
  @JsonAnySetter
  val details: Map<String, Any> = hashMapOf()
)