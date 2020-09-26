package com.guidetap.stark.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class PageInfoExtractorTest {

  private val pageInfoExtractor = PageInfoExtractor()

  @ParameterizedTest
  @MethodSource("getData")
  fun testExtractor(url: String, expected: String) {
    assertThat(pageInfoExtractor.extractPageInfo(url)).isEqualTo(expected)
  }

  companion object {
    @JvmStatic
    fun getData(): Stream<Arguments> =
      Stream.of(
        Arguments.of(
          "<https://mocchi-app.myshopify.com/admin/api/2020-07/customers.json?limit=50&page_info=eyJsYXN0X2lkIjo0MTY0NzE0Mjk5NTQyLCJsYXN0X3ZhbHVlIjoxNjAxMTAwNzg3MDAwLCJkaXJlY3Rpb24iOiJuZXh0In0>; rel=\"next\"",
          "https://mocchi-app.myshopify.com/admin/api/2020-07/customers.json?limit=50&page_info=eyJsYXN0X2lkIjo0MTY0NzE0Mjk5NTQyLCJsYXN0X3ZhbHVlIjoxNjAxMTAwNzg3MDAwLCJkaXJlY3Rpb24iOiJuZXh0In0"
        ),
        Arguments.of(
          "<https://mocchi-app.myshopify.com/>; rel=\"next\"",
          "https://mocchi-app.myshopify.com/"
        ),
        Arguments.of(
          "<https://myshopify.com/admin/>; rel=\"next\", <https://{shop}.myshopify.com/admin/api/{version}/products.json?page_info={page_info}&limit={limit}>; rel=\"previous\"",
          "https://myshopify.com/admin/"
        ),
        Arguments.of(
          "<https://previous.myshopify.com/admin/>; rel=\"previous\", <https://next.myshopify.com/admin/>; rel=\"next\"",
          "https://next.myshopify.com/admin/"
        ),
      )
  }
}