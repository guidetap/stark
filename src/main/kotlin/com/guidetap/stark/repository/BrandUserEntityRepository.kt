package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.BrandUserEntity
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.springframework.stereotype.Repository

@Repository
class BrandUserEntityRepository(
    mongoDatabase: CoroutineDatabase
) {
  private val col = mongoDatabase.getCollection<BrandUserEntity>("BrandUsers")

  suspend fun insert(entity: BrandUserEntity): BrandUserEntity =
      col.findOneAndReplace(
          BrandUserEntity::auth0Id eq entity.auth0Id,
          entity,
          FindOneAndReplaceOptions()
              .upsert(true)
              .returnDocument(ReturnDocument.AFTER)
      )
}