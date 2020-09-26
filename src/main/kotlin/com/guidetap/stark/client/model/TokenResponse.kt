package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponse(
  @JsonProperty("access_token")
  val accessToken: String,
  @JsonProperty("id_token")
  val idToken: String?,
  val scope: String,
  @JsonProperty("expires_in")
  val expireIn: Long,
  @JsonProperty("token_type")
  val tokenType: String
)