package com.ozcoin.cookiepang.domain.contract

interface ContractRepository {

    suspend fun getNumOfHammer(userId: String): Int

    suspend fun getNumOfKlaytn(userId: String): Int

    suspend fun isWalletApproved(userId: String): Boolean

    suspend fun isOnSaleCookie(nftTokenId: Int): Boolean

    suspend fun isCookieHidden(cookieId: Int): Boolean

    suspend fun getCookieContractAddress(): String

    suspend fun getHammerContractAddress(): String
}
