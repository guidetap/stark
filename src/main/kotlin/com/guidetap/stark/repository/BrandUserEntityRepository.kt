package com.guidetap.stark.repository

import com.guidetap.stark.repository.model.BrandUserEntity
import com.guidetap.stark.repository.model.SyncData
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.SetTo
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

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

  suspend fun updateCustomerLastSyncDate(auth0Id: String, lastSync: ZonedDateTime?): BrandUserEntity? =
    col.findOneAndUpdate(
      BrandUserEntity::auth0Id eq auth0Id,
      set(
        SetTo(BrandUserEntity::syncData / SyncData::lastCustomerDate, lastSync)
      ),
      FindOneAndUpdateOptions()
        .returnDocument(ReturnDocument.AFTER)
    )

  suspend fun updateOrderLastSyncDate(auth0Id: String, lastSync: ZonedDateTime?): BrandUserEntity? =
    col.findOneAndUpdate(
      BrandUserEntity::auth0Id eq auth0Id,
      set(
        SetTo(BrandUserEntity::syncData / SyncData::lastOrderDate, lastSync)
      ),
      FindOneAndUpdateOptions()
        .returnDocument(ReturnDocument.AFTER)
    )

  suspend fun findById(auth0Id: String): BrandUserEntity? =
    col.findOne(BrandUserEntity::auth0Id eq auth0Id)

  fun findAll(): Flow<BrandUserEntity> =
    col.find().toFlow()
}