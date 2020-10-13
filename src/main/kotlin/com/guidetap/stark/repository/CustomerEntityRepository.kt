package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.repository.model.LastUpdatedGroupResult
import com.mongodb.client.model.Aggregates.group
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.max
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

  suspend fun findLastUpdated(): LastUpdatedGroupResult? =
    col.aggregate<LastUpdatedGroupResult>(
      listOf(
        group(
          null,
          listOf(
            LastUpdatedGroupResult::property max CustomerEntity::updatedAt
          )
        )
      )
    )
      .first()

  suspend fun findById(shopifyId: Long): CustomerEntity? =
    col.findOne(CustomerEntity::shopifyId eq shopifyId)
}