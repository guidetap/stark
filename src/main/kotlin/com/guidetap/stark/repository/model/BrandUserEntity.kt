package com.guidetap.stark.repository.model

data class BrandUserEntity(
  val auth0Id: String,
  val nickname: String?,
  val name: String?,
  val email: String?,
)