package com.guidetap.stark.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class UserResponse(
  val email: String,
  val domain: String,
  val identities: List<Identity>
) : Serializable

data class Identity(
  @JsonProperty("access_token")
  val accessToken: String,
  val provider: String,
  @JsonProperty("user_id")
  val user_id: String,
  val connection: String,
  val isSocial: Boolean
)