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
    override suspend fun makeACookie(editCookie: EditCookie): String = withContext(Dispatchers.IO) {
        var makeACookieResult = ""
        getDataResult(cookieRemoteDataSource.makeACookie(editCookie.toMakeRequestRemote())) {
            makeACookieResult = it.id.toString()
        }

        makeACookieResult
    }

    override suspend fun editCookieInfo(editCookie: EditCookie): Boolean =
        withContext(Dispatchers.IO) {
            var editCookieInfoResult = false
            val response = cookieRemoteDataSource.updateCookieInfo(
                editCookie.cookieId.toString(),
                editCookie.hammerCost.toInt(),
                editCookie.userId.toDataUserId()
            )
            getDataResult(response) {
                editCookieInfoResult = true
            }

            editCookieInfoResult
        }
}
