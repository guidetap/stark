package com.guidetap.stark.configuration

import org.jeasy.random.EasyRandom
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class EasyBeansConfiguration {

  @Bean
  fun easyRandom(): EasyRandom = EasyRandom()
}