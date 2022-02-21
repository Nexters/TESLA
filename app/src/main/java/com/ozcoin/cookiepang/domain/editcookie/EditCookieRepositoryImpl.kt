package com.ozcoin.cookiepang.domain.editcookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.toMakeRequestRemote
import com.ozcoin.cookiepang.data.request.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditCookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : EditCookieRepository {
    override suspend fun makeACookie(editCookie: EditCookie): String = withContext(Dispatchers.IO) {
        var makeACookieResult = ""
        val response = cookieRemoteDataSource.makeACookie(editCookie.toMakeRequestRemote())
        if (response is NetworkResult.Success) {
            makeACookieResult = response.response.id.toString()
        }
        makeACookieResult
    }

    override suspend fun editCookieInfo(editCookie: EditCookie): Boolean =
        withContext(Dispatchers.IO) {
            var editCookieInfoResult = false
            val response = cookieRemoteDataSource.updateCookieInfo(
                editCookie.cookieId.toString(),
                editCookie.hammerCost.toInt(),
                editCookie.userId.toInt()
            )
            if (response is NetworkResult.Success)
                editCookieInfoResult = true
            editCookieInfoResult
        }
}
