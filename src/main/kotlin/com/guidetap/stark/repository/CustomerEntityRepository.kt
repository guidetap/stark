package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.CustomerEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.springframework.stereotype.Repository

@Repository
class CustomerEntityRepository(
    mongoDatabase: CoroutineDatabase
) {

  private val col = mongoDatabase.getCollection<CustomerEntity>("Customers")

  suspend fun insert(entity: CustomerEntity) =
      col.insertOne(entity)
}