package com.guidetap.stark.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Component
@Validated
@ConfigurationProperties("auth0")
class Auth0Properties {

  var brand = App()

  var user = App()

  class App {
    @NotBlank
    var clientId: String = ""

    @NotBlank
    var clientSecret: String = ""

    @NotBlank
    var audience: String = ""
  }
}