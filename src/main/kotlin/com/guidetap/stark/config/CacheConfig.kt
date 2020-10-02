package com.guidetap.stark.config

import com.guidetap.stark.client.model.UserResponse
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.xml.XmlConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource


@Configuration
class CacheConfig {

  @Bean
  fun cacheManager(
    @Value("classpath:ehcache.xml")
    resourceFile: Resource
  ): CacheManager =
    CacheManagerBuilder.newCacheManager(
      XmlConfiguration(resourceFile.url)
    )
      .also { it.init() }

  @Bean
  fun userDataCache(cacheManager: CacheManager): Cache<String, UserResponse> =
    cacheManager.getCache(
      "user-data",
      String::class.java,
      UserResponse::class.java,
    )

}