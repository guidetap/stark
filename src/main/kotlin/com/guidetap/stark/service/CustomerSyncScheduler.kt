package com.guidetap.stark.service

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class CustomerSyncScheduler(
    private val brandUserEntityService: BrandUserEntityService,
    private val shopifySyncService: ShopifySyncService
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @Scheduled(fixedDelay = 10_000)
  fun syncCustomers() {
    log.info("process='syncCustomers' message='sync job has been started'")
    runBlocking {
      brandUserEntityService.findAll()
          .collect {
            log.info("process='syncCustomers' message='next customer picked' userId='${it.auth0Id}'")
            val synced = shopifySyncService.syncShopifyCustomersFor(it.auth0Id)
                .count()
            log.info("process='syncCustomers' message='customer sync completed' userId='${it.auth0Id}' count='$synced'")
          }
    }
    log.info("process='syncCustomers' message='sync job has been completed'")
  }
}