package com.ozcoin.cookiepang.data.contract

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class ContractRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun isOnSaleCookie(cookieId: Int) =
        safeApiCall {
            apiService.isOnSaleCookie("")
        }

    suspend fun isCookieHidden(cookieId: Int) =
        safeApiCall {
            apiService.isCookieHidden("")
        }

    suspend fun getCookieContractAddress() =
        safeApiCall {
            apiService.getCookieContractAddress()
        }

    suspend fun getHammerContractAddress() =
        safeApiCall {
            apiService.getCookieContractAddress()
        }
}
