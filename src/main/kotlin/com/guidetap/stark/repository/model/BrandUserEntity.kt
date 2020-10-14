package com.guidetap.stark.repository.model

import java.time.ZonedDateTime

data class BrandUserEntity(
  val auth0Id: String,
  val nickname: String?,
  val name: String?,
  val email: String?,
  val lastCustomerSyncDate: ZonedDateTime?,
  val lastOrderSyncDate: ZonedDateTime?
)