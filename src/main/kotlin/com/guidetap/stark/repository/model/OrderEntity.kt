package com.guidetap.stark.repository.model

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.time.ZonedDateTime

data class OrderEntity(
  val shopifyId: Long,
  val createdAt: ZonedDateTime,
  val updatedAt: ZonedDateTime,

  @get:JsonAnyGetter
  @JsonAnySetter
  val details: Map<String, Any> = hashMapOf()
)
