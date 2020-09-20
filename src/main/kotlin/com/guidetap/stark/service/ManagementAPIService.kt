package com.guidetap.stark.service

import com.guidetap.stark.client.ManagementClient
import org.springframework.stereotype.Service

@Service
class ManagementAPIService(
    private val managementClient: ManagementClient
) {

  suspend fun getManagementToken() =
      managementClient.getManagementToken()

  suspend fun getUserData(userId: String) =
      managementClient.getUserById(userId, managementClient.getManagementToken().accessToken)

}