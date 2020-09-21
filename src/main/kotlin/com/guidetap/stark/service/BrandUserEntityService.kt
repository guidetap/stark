package com.guidetap.stark.service

import com.guidetap.stark.repository.BrandUserEntityRepository
import com.guidetap.stark.repository.model.BrandUserEntity
import org.springframework.stereotype.Service

@Service
class BrandUserEntityService(
    private val brandUserEntityRepository: BrandUserEntityRepository
) {

  suspend fun insert(entity: BrandUserEntity) = brandUserEntityRepository.insert(entity)
}