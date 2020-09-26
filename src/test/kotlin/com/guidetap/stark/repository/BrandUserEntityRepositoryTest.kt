package com.guidetap.stark.repository

import com.guidetap.stark.AbstractIntegrationTest
import com.guidetap.stark.repository.model.BrandUserEntity
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class BrandUserEntityRepositoryTest(
  @Autowired
  private val brandUserEntityRepository: BrandUserEntityRepository
) : AbstractIntegrationTest() {

  @Test
  fun `should insert brand entity to database`() {
    runBlocking {
      val expected = BrandUserEntity(auth0Id = "auth0Id")
      val actual = brandUserEntityRepository.insert(expected)
      assertThat(actual).isEqualTo(expected)

      assertThat(brandUserEntityRepository.findById(expected.auth0Id))
        .isEqualTo(expected)
    }
  }

  @Test
  fun `should not insert brand entity to database for the second time`() {
    runBlocking {
      val expected = BrandUserEntity(auth0Id = "auth0Id")
      brandUserEntityRepository.insert(expected)
      val actual = brandUserEntityRepository.insert(expected)
      assertThat(actual).isEqualTo(expected)

      assertThat(brandUserEntityRepository.findById(expected.auth0Id))
        .isEqualTo(expected)
    }
  }
}