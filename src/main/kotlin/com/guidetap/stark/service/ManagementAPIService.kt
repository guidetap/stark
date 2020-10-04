package com.guidetap.stark.service

import com.guidetap.stark.client.ManagementClient
import com.guidetap.stark.client.model.UserResponse
import org.ehcache.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ManagementAPIService(
  private val managementClient: ManagementClient,
  private val cache: Cache<String, UserResponse>
) {

  suspend fun getManagementToken() =
    managementClient.getBrandManagementToken()

  @Cacheable("user-data")
  suspend fun getUserData(userId: String): UserResponse =
    cache.get(userId) ?: managementClient.getUserById(userId, managementClient.getBrandManagementToken().accessToken)
      .also { cache.put(userId, it) }

}