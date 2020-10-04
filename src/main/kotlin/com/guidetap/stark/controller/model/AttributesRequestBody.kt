package com.guidetap.stark.controller.model

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonGetter

data class AttributesRequestBody(
  @JsonAnySetter
  @get:JsonGetter
  val attributes: Map<String, AttributeMetadata> = HashMap()
)


data class AttributeMetadata(
  val type: String
)