package com.guidetap.stark.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
          "/api/v1/login/code/brand/exchange",
          "/api/v1/login/code/user/exchange",
        )
        .permitAll()
        .pathMatchers(HttpMethod.GET, "/brand/api/v1/attributes").hasAuthority("SCOPE_read:brand")
        .pathMatchers(HttpMethod.GET, "/brand/api/v1/customer").hasAuthority("SCOPE_read:brand")
        .pathMatchers(HttpMethod.PUT, "/brand/api/v1/attributes").hasAuthority("SCOPE_write:brand")
        .pathMatchers(HttpMethod.PUT, "/brand/api/v1/customer/sync").hasAuthority("SCOPE_write:brand")
        .anyExchange().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
    }
      .build()
}
