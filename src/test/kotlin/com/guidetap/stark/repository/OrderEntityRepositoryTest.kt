package com.guidetap.stark.repository

import com.guidetap.stark.AbstractIntegrationTest
import com.guidetap.stark.repository.model.OrderEntity
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class OrderEntityRepositoryTest(
  @Autowired
  private val orderEntityRepository: OrderEntityRepository,
  @Autowired
  private val easyRandom: EasyRandom
) : AbstractIntegrationTest() {

  @Test
  fun `should insert order entity to database`() {
    runBlocking {
      val expected = generateOrderEntity()
      val actual = orderEntityRepository.insert(expected)
      assertThat(actual)
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "details"
        )
      assertThat(actual.details).containsAllEntriesOf(expected.details)

      assertThat(orderEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "details",
        )
      assertThat(orderEntityRepository.findById(expected.shopifyId)?.details).containsAllEntriesOf(expected.details)
    }
  }

  @Test
  fun `should not insert order entity to database for the second time`() {
    runBlocking {
      val expected = generateOrderEntity()
      orderEntityRepository.insert(expected)
      val actual = orderEntityRepository.insert(expected)
      assertThat(actual)
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "details",
          )
      assertThat(actual.details).containsAllEntriesOf(expected.details)

      assertThat(orderEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "details",
        )
      assertThat(orderEntityRepository.findById(expected.shopifyId)?.details).containsAllEntriesOf(expected.details)
    }
  }

  @Test
  fun `should find maximum for aggregation`() {
    runBlocking {
      val customerEntities = listOf(
        generateOrderEntity(),
        generateOrderEntity(),
        generateOrderEntity()
      )

      customerEntities.forEach {
        orderEntityRepository.insert(it)
      }

      val expected = customerEntities.maxByOrNull { it.updatedAt } ?: throw IllegalStateException()

      assertThat(orderEntityRepository.findById(expected.shopifyId))
        .isEqualToIgnoringGivenFields(
          expected,
          "createdAt",
          "updatedAt",
          "details",
        )
      assertThat(orderEntityRepository.findById(expected.shopifyId)?.details).containsAllEntriesOf(expected.details)

      assertThat(orderEntityRepository.findLastUpdated()?.property)
        .isEqualToIgnoringNanos(expected.updatedAt)
    }
  }

  private fun generateOrderEntity() =
    fixOrderEntity {
      easyRandom.nextObject(OrderEntity::class.java)
    }

  private fun fixOrderEntity(generate: () -> OrderEntity): OrderEntity =
    generate().let { entity ->
      entity.details.keys.map { it to it }.toMap()
        .let { entity.copy(details = it) }
    }

}