package com.ozcoin.cookiepang.domain.editcookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.toMakeRequestRemote
import com.ozcoin.cookiepang.data.request.NetworkResult
import javax.inject.Inject

class EditCookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : EditCookieRepository {
    override suspend fun makeACookie(editCookie: EditCookie): String {
        var makeACookieResult = ""
        val response = cookieRemoteDataSource.makeACookie(editCookie.toMakeRequestRemote())
        if (response is NetworkResult.Success) {
            makeACookieResult = response.response.id.toString()
        }

        return makeACookieResult
    }

    override suspend fun editCookieInfo(editCookie: EditCookie) {}
}
