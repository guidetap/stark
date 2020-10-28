package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.CustomerEntity
import com.guidetap.stark.repository.model.LastUpdatedGroupResult
import com.guidetap.stark.repository.model.OrderEntity
import com.mongodb.client.model.Aggregates.group
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.max
import org.springframework.stereotype.Repository

@Repository
class OrderEntityRepository(
  mongoDatabase: CoroutineDatabase
) {

  private val col = mongoDatabase.getCollection<OrderEntity>("Orders")

  suspend fun insert(entity: OrderEntity): OrderEntity =
    col.findOneAndReplace(
      OrderEntity::shopifyId eq entity.shopifyId,
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
            LastUpdatedGroupResult::property max OrderEntity::updatedAt
          )
        )
      )
    )
      .first()

  suspend fun findById(shopifyId: Long): OrderEntity? =
    col.findOne(CustomerEntity::shopifyId eq shopifyId)
}