package com.guidetap.stark.service

import com.guidetap.stark.repository.CustomerEntityRepository
import com.guidetap.stark.repository.model.CustomerEntity
import org.springframework.stereotype.Service

@Service
class CustomerEntityService(
    private val customerEntityRepository: CustomerEntityRepository
) {

  suspend fun insert(entity: CustomerEntity) =
      customerEntityRepository.insert(entity)
}