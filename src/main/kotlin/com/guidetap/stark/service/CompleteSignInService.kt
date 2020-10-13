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
            tokenParserService.getIdFromToken(jwtToken)
              .let { parsedToken ->
                brandUserEntityService.insert(
                  BrandUserEntity(
                    auth0Id = parsedToken.name,
                    nickname = parsedToken.token.claims["nickname"] as? String,
                    name = parsedToken.token.claims["name"] as? String,
                    email = parsedToken.token.claims["email"] as? String,
                    lastSyncDate = null
                  )
                )
              }
          }
      }

  suspend fun completeUserSignIn(code: String) =
    codeExchangeService.exchangeUserCode(code)
}