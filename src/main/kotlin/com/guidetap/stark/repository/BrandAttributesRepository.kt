package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.BrandAttributesEntity
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.springframework.stereotype.Repository

@Repository
class BrandAttributesRepository(
  mongoDatabase: CoroutineDatabase
) {
  private val col = mongoDatabase.getCollection<BrandAttributesEntity>("BrandAttributes")

  suspend fun saveAttributes(brandAttributesEntity: BrandAttributesEntity) =
    col.findOneAndReplace(
      BrandAttributesEntity::auth0Id eq brandAttributesEntity.auth0Id,
      brandAttributesEntity,
      FindOneAndReplaceOptions()
        .upsert(true)
        .returnDocument(ReturnDocument.AFTER)
    )

  fun findAll(name: String): Flow<BrandAttributesEntity> =
    col.find(BrandAttributesEntity::auth0Id eq name)
      .toFlow()
}