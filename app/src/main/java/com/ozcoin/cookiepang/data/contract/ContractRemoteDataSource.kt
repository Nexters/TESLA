package com.ozcoin.cookiepang.data.contract

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class ContractRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun isWalletApproved(userId: Int) =
        safeApiCall {
            apiService.getIsWalletApproved(userId)
        }

    suspend fun isOnSaleCookie(nftTokenId: Int) =
        safeApiCall {
            apiService.isOnSaleCookie(nftTokenId)
        }

    suspend fun isCookieHidden(nftTokenId: Int) =
        safeApiCall {
            apiService.isCookieHidden(nftTokenId)
        }

    suspend fun getCookieContractAddress() =
        safeApiCall {
            apiService.getCookieContractAddress()
        }

    suspend fun getHammerContractAddress() =
        safeApiCall {
            apiService.getHammerContractAddress()
        }

    suspend fun getNumOfHammer(userId: Int) =
        safeApiCall {
            apiService.getNumOfHammer(userId)
        }

    suspend fun getNumOfKlay(userId: Int) =
        safeApiCall {
            apiService.getNumOfKlay(userId)
        }
}
