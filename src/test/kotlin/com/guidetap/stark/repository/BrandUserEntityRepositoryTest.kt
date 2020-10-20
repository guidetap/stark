package com.guidetap.stark.repository

import com.guidetap.stark.AbstractIntegrationTest
import com.guidetap.stark.repository.model.BrandUserEntity
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.ZonedDateTime
import java.util.Comparator
import kotlin.math.exp

internal class BrandUserEntityRepositoryTest(
  @Autowired
  private val brandUserEntityRepository: BrandUserEntityRepository,
  @Autowired
  private val random: EasyRandom,
) : AbstractIntegrationTest() {

  @Test
  fun `should insert brand entity to database`() {
    runBlocking {
      val expected = random.nextObject(BrandUserEntity::class.java)
      val actual = brandUserEntityRepository.insert(expected)
      assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("syncData")
        .isEqualTo(expected)

      assertThat(brandUserEntityRepository.findById(expected.auth0Id))
        .usingRecursiveComparison()
        .ignoringFields("syncData")
        .isEqualTo(expected)
    }
  }

  @Test
  fun `should not insert brand entity to database for the second time`() {
    runBlocking {
      val expected = random.nextObject(BrandUserEntity::class.java)
      brandUserEntityRepository.insert(expected)
      val actual = brandUserEntityRepository.insert(expected)
      assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("syncData")
        .isEqualTo(expected)

      assertThat(brandUserEntityRepository.findById(expected.auth0Id))
        .usingRecursiveComparison()
        .ignoringFields("syncData")
        .isEqualTo(expected)
    }
  }

  @Test
  fun `should update customer last synced date`() {
    runBlocking {
      val entity = random.nextObject(BrandUserEntity::class.java)
      brandUserEntityRepository.insert(entity)

      val lastSync = ZonedDateTime.now()
      val actual = brandUserEntityRepository.updateCustomerLastSyncDate(
        auth0Id = entity.auth0Id,
        lastSync = lastSync
      )
      assertThat(actual?.syncData?.lastCustomerDate)
        .isEqualToIgnoringNanos(lastSync)
      assertThat(brandUserEntityRepository.findById(entity.auth0Id)?.syncData?.lastCustomerDate)
        .isEqualToIgnoringNanos(lastSync)
    }
  }
}