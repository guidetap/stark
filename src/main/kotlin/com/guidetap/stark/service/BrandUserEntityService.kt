package com.guidetap.stark.service

import com.guidetap.stark.repository.BrandUserEntityRepository
import com.guidetap.stark.repository.model.BrandUserEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class BrandUserEntityService(
  private val brandUserEntityRepository: BrandUserEntityRepository
) {

  suspend fun insert(entity: BrandUserEntity) = brandUserEntityRepository.insert(entity)

  suspend fun findById(auth0Id: String) = brandUserEntityRepository.findById(auth0Id)

  suspend fun updateLastSyncDate(auth0Id: String, lastSync: ZonedDateTime?) =
    brandUserEntityRepository.updateCustomerLastSyncDate(auth0Id, lastSync)

  fun findAll(): Flow<BrandUserEntity> = brandUserEntityRepository.findAll()
}