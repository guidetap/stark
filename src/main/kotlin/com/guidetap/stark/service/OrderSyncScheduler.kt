package com.guidetap.stark.service

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class OrderSyncScheduler(
  private val brandUserEntityService: BrandUserEntityService,
  private val shopifySyncService: ShopifySyncService
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @Scheduled(fixedRate = 10_000)
  fun syncOrders() {
    log.info("process='syncOrders' message='sync job has been started'")
    runBlocking {
      brandUserEntityService.findAll()
        .collect {
          log.info("process='syncOrders' message='next brand picked' userId='${it.auth0Id}'")
          val synced = shopifySyncService.syncShopifyOrdersFor(it.auth0Id)
            .count()
          log.info("process='syncOrders' message='orders sync completed' userId='${it.auth0Id}' count='$synced'")
        }
    }
    log.info("process='syncOrders' message='sync job has been completed'")
  }
}