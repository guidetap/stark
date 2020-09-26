package com.guidetap.stark

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
class AbstractIntegrationTest {

  private val container: KDockerComposeContainer =
    KDockerComposeContainer(
      File("src/test/resources/compose-test.yml")
    )
      .withExposedService("mongo", 27017, Wait.forListeningPort())

  init {
    container.start()
    System.setProperty(
      "spring.data.mongodb.port",
      container.getServicePort("mongo", 27017).toString()
    )
  }

}

class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)