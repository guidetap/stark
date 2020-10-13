package com.guidetap.stark

import com.guidetap.stark.configuration.EasyBeansConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File

@ExtendWith(SpringExtension::class)
@Import(EasyBeansConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AbstractIntegrationTest {

  companion object {
    private val container: KDockerComposeContainer =
      KDockerComposeContainer(
        File("src/test/resources/compose-test.yml")
      )
        .withLocalCompose(true)
        .withExposedService("mongo", 27017, Wait.forListeningPort())

    init {
      container.start()
    }

    @JvmStatic
    @DynamicPropertySource
    private fun mongoDBProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.data.mongodb.port") {
        container.getServicePort("mongo", 27017).toString()
      }
    }

  }
}

class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)