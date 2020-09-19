package com.guidetap.stark.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Component
@Validated
@ConfigurationProperties("login.client")
class LoginClientProperties {

  @NotBlank
  var webRedirectUrl: String = ""
}