package com.ozcoin.cookiepang.domain.editcookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.toMakeRequestRemote
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.getDataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditCookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : EditCookieRepository {
    override suspend fun makeACookie(
        userId: String,
        txHash: String,
        editCookie: EditCookie
    ): String = withContext(Dispatchers.IO) {
        var makeACookieResult = ""
        val response = cookieRemoteDataSource.makeACookie(
            editCookie.toMakeRequestRemote(
                userId.toDataUserId(),
                txHash
            )
        )
        getDataResult(response) {
            makeACookieResult = it.id.toString()
        }

        makeACookieResult
    }

    override suspend fun editCookieInfo(userId: String, editCookie: EditCookie): Boolean =
        withContext(Dispatchers.IO) {
            var editCookieInfoResult = false
            val response = cookieRemoteDataSource.updateCookieInfo(
                editCookie.cookieId.toString(),
                editCookie.hammerCost.toInt(),
                userId.toDataUserId()
            )
            getDataResult(response) {
                editCookieInfoResult = true
            }

            editCookieInfoResult
        }
}
