package com.ozcoin.cookiepang.domain.contract

interface ContractRepository {

    suspend fun getMakeCookieTaxPrice(): Int

    suspend fun getNumOfHammerBalance(userId: String): Int

    suspend fun getNumOfKlaytnBalance(userId: String): Int

    suspend fun isWalletApproved(userId: String): Boolean

    suspend fun isOnSaleCookie(nftTokenId: Int): Boolean

    suspend fun isCookieHidden(cookieId: Int): Boolean

    suspend fun getCookieContractAddress(): String

    suspend fun getHammerContractAddress(): String
}
