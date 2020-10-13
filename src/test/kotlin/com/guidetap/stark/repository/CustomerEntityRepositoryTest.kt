package com.guidetap.stark.repository

import com.guidetap.stark.AbstractIntegrationTest
import com.guidetap.stark.repository.model.CustomerEntity
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class CustomerEntityRepositoryTest(
  @Autowired
  private val customerEntityRepository: CustomerEntityRepository,
  @Autowired
  private val easyRandom: EasyRandom
) : AbstractIntegrationTest() {


  @Test
  fun `should insert brand entity to database`() {
    runBlocking {
      val expected = easyRandom.nextObject(CustomerEntity::class.java)
      val actual = customerEntityRepository.insert(expected)
      assertThat(actual)
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "acceptsMarketingUpdatedAt",
        )

      assertThat(customerEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "acceptsMarketingUpdatedAt",
        )
    }
  }

  @Test
  fun `should not insert brand entity to database for the second time`() {
    runBlocking {
      val expected = easyRandom.nextObject(CustomerEntity::class.java)
      customerEntityRepository.insert(expected)
      val actual = customerEntityRepository.insert(expected)
      assertThat(actual)
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "acceptsMarketingUpdatedAt",
        )

      assertThat(customerEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "acceptsMarketingUpdatedAt",
        )
    }
  }

  @Test
  fun `should find maximum for aggregation`() {
    runBlocking {
      val customerEntities = listOf(
        easyRandom.nextObject(CustomerEntity::class.java),
        easyRandom.nextObject(CustomerEntity::class.java),
        easyRandom.nextObject(CustomerEntity::class.java)
      )

      customerEntities.forEach {
        customerEntityRepository.insert(it)
      }

      val expected = customerEntities.maxByOrNull { it.updatedAt } ?: throw IllegalStateException()

      assertThat(customerEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "acceptsMarketingUpdatedAt",
        )

      assertThat(customerEntityRepository.findLastUpdated()?.property)
        .isEqualToIgnoringNanos(expected.updatedAt)
    }
  }
}