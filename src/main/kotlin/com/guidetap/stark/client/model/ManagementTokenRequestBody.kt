package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ManagementTokenRequestBody(
  @JsonProperty("client_id")
  val clientId: String,
  @JsonProperty("client_secret")
  val clientSecret: String,
  @JsonProperty("audience")
  val audience: String,
  @JsonProperty("grant_type")
  val grantType: String
)