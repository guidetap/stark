package com.guidetap.stark.repository.model

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

data class BrandAttributesEntity(
  @get:JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val _id: String? = null,
  val auth0Id: String,
  @JsonAnySetter
  @get:JsonAnyGetter
  val attributes: Map<String, AttributeMetadataEntity> = HashMap()
)

data class AttributeMetadataEntity(
  val type: String
)