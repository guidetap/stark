package com.guidetap.stark.repository.model

import java.time.LocalDateTime

data class BrandUserEntity(
  val auth0Id: String,
  val nickname: String?,
  val name: String?,
  val email: String?,
  val lastSyncDate: LocalDateTime?
)