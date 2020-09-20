package com.guidetap.stark.converter

interface Converter<In, Out> {
  fun convert(source: In): Out
}