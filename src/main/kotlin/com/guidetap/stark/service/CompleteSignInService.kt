package com.guidetap.stark.service

import com.guidetap.stark.repository.model.BrandUserEntity
import org.springframework.stereotype.Service

@Service
class CompleteSignInService(
  private val codeExchangeService: CodeExchangeService,
  private val brandUserEntityService: BrandUserEntityService,
  private val tokenParserService: TokenParserService
) {

  suspend fun completeBrandSignIn(code: String) =
    codeExchangeService.exchangeBrandCode(code)
      .also {
        it.idToken
          ?.also { jwtToken ->
            brandUserEntityService.insert(
              BrandUserEntity(
                auth0Id = tokenParserService.getIdFromToken(jwtToken)
              )
            )
          }
      }

  suspend fun completeUserSignIn(code: String) =
    codeExchangeService.exchangeUserCode(code)
}