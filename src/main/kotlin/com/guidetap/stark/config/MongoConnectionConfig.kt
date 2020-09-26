package com.guidetap.stark.config

import com.mongodb.reactivestreams.client.MongoClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.withKMongo
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConnectionConfig {

  @Bean
  fun mongoDatabase(mongoClient: MongoClient, mongoProperties: MongoProperties): CoroutineDatabase =
    mongoClient.getDatabase(mongoProperties.database).withKMongo().coroutine

}