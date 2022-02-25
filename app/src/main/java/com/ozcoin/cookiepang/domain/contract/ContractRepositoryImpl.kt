package com.ozcoin.cookiepang.domain.contract

import com.ozcoin.cookiepang.data.contract.ContractRemoteDataSource
import com.ozcoin.cookiepang.extensions.getDataResult
import com.ozcoin.cookiepang.utils.CoinUnitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    private val contractRemoteDataSource: ContractRemoteDataSource
) : ContractRepository {

    override suspend fun getMakeCookieTaxPrice(): Int =
        withContext(Dispatchers.IO) {
            var result = 0
            getDataResult(contractRemoteDataSource.getMakeCookieTaxPrice()) {
                result = it.price
            }
            result
        }

    override suspend fun getNumOfHammerBalance(userId: String): BigInteger =
        withContext(Dispatchers.IO) {
            var result = BigInteger("0")
            getDataResult(contractRemoteDataSource.getNumOfHammer(userId)) {
                result = CoinUnitUtil.convertToKlaytnUnit(it.balance)
            }
            result
        }

    override suspend fun getNumOfKlaytnBalance(userId: String): BigInteger =
        withContext(Dispatchers.IO) {
            var result = BigInteger("0")
            getDataResult(contractRemoteDataSource.getNumOfKlay(userId)) {
                result = CoinUnitUtil.convertToKlaytnUnit(it.balance)
            }
            result
        }

    override suspend fun isWalletApproved(userId: String): Boolean =
        withContext(Dispatchers.IO) {
            var result = false
            getDataResult(contractRemoteDataSource.isWalletApproved(userId)) {
                result = it.answer
            }
            result
        }

    override suspend fun isOnSaleCookie(nftTokenId: Int): Boolean =
        withContext(Dispatchers.IO) {
            var result = false
            getDataResult(contractRemoteDataSource.isOnSaleCookie(nftTokenId)) {
                result = it.answer
            }
            result
        }

    override suspend fun isCookieHidden(cookieId: Int): Boolean =
        withContext(Dispatchers.IO) {
            false
        }

    override suspend fun getCookieContractAddress(): String =
        withContext(Dispatchers.IO) {
            var result = ""
            getDataResult(contractRemoteDataSource.getCookieContractAddress()) {
                result = it.address
            }
            result
        }

    override suspend fun getHammerContractAddress(): String =
        withContext(Dispatchers.IO) {
            var result = ""
            getDataResult(contractRemoteDataSource.getHammerContractAddress()) {
                result = it.address
            }
            result
        }
}
