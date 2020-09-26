package com.guidetap.stark.client

import org.springframework.stereotype.Component

@Component
class PageInfoExtractor {
  fun extractPageInfo(headerValue: String): String? =
    """.*<(.*)>; rel="next".*""".toRegex().find(headerValue)?.destructured?.component1()
}