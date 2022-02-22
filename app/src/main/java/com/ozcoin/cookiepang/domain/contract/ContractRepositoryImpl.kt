package com.ozcoin.cookiepang.domain.contract

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor() : ContractRepository {

    override suspend fun getNumOfHammer(): Int =
        withContext(Dispatchers.IO) {
            1
        }

    override suspend fun getNumOfKlaytn(): Int =
        withContext(Dispatchers.IO) {
            1
        }

    override suspend fun getIsWalletApproved(): Boolean =
        withContext(Dispatchers.IO) {
            false
        }

    override suspend fun isOnSaleCookie(): Boolean =
        withContext(Dispatchers.IO) {
            false
        }

    override suspend fun isCookieHidden(cookieId: Int): Boolean =
        withContext(Dispatchers.IO) {
            false
        }

    override suspend fun getCookieContractAddress(): String? =
        withContext(Dispatchers.IO) {
            ""
        }

    override suspend fun getHammerContractAddress(): String? =
        withContext(Dispatchers.IO) {
            ""
        }
}
