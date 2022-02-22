package com.ozcoin.cookiepang.domain.contract

interface ContractRepository {

    suspend fun getNumOfHammer(): Int

    suspend fun getNumOfKlaytn(): Int

    suspend fun getIsWalletApproved(): Boolean

    suspend fun isOnSaleCookie(): Boolean

    suspend fun isCookieHidden(cookieId: Int): Boolean

    suspend fun getCookieContractAddress(): String?

    suspend fun getHammerContractAddress(): String?
}
