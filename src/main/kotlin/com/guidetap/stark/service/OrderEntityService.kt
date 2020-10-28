package com.guidetap.stark.service

import com.guidetap.stark.repository.CustomerEntityRepository
import com.guidetap.stark.repository.OrderEntityRepository
import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.repository.model.OrderEntity
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class OrderEntityService(
  val orderEntityRepository: OrderEntityRepository
) {

  suspend fun insert(entity: OrderEntity) =
    orderEntityRepository.insert(entity)

  suspend fun findLastUpdated(): ZonedDateTime? =
    orderEntityRepository.findLastUpdated()?.property
}