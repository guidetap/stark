package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.CustomerEntity
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.springframework.stereotype.Repository

@Repository
class CustomerEntityRepository(
  mongoDatabase: CoroutineDatabase
) {

  private val col = mongoDatabase.getCollection<CustomerEntity>("Customers")

  suspend fun insert(entity: CustomerEntity): CustomerEntity =
    col.findOneAndReplace(
      CustomerEntity::shopifyId eq entity.shopifyId,
      entity,
      FindOneAndReplaceOptions()
        .upsert(true)
        .returnDocument(ReturnDocument.AFTER)
    )
}