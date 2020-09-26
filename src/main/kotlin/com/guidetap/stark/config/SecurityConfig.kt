package com.guidetap.stark.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfig {

  @Bean
  fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
    http.also {
      it
        .csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .authorizeExchange()
        .pathMatchers(
          "/api/v1/login/code/*",
          "/api/v1/login/code/exchange",
          "/api/v1/login/code/exchange/",
        ).permitAll()
        .anyExchange().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
    }
      .build()
}
