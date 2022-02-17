package com.ozcoin.cookiepang.domain.editcookie

import javax.inject.Inject

class EditCookieRepositoryImpl @Inject constructor() : EditCookieRepository {

    override suspend fun makeACookie(editCookie: EditCookie) {}

    override suspend fun editCookieInfo(editCookie: EditCookie) {}

    override suspend fun getResult(): Boolean {
        return true
    }
}
