package com.guidetap.stark.service

import io.ktor.http.*
import org.springframework.stereotype.Service

@Service
class UrlParser {

  fun extractParamUrl(url: String?, param: String): String? =
      url?.let { Url(it) }?.parameters?.get(param)
}