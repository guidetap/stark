package com.guidetap.stark.service

import com.guidetap.stark.controller.model.AttributesRequestBody
import com.guidetap.stark.repository.BrandAttributesRepository
import com.guidetap.stark.repository.model.AttributeMetadataEntity
import com.guidetap.stark.repository.model.BrandAttributesEntity
import org.springframework.stereotype.Service

@Service
class BrandAttributesService(
  private val brandAttributesRepository: BrandAttributesRepository
) {

  suspend fun saveAttributes(auth0Id: String, attributesRequestBody: AttributesRequestBody) =
    brandAttributesRepository.saveAttributes(
      BrandAttributesEntity(
        auth0Id = auth0Id,
        attributes = attributesRequestBody.attributes
          .entries.map {
            it.key to AttributeMetadataEntity(it.value.type)
          }
          .toMap()
      )
    )
}