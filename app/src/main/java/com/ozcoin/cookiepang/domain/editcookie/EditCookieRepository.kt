package com.ozcoin.cookiepang.domain.editcookie

interface EditCookieRepository {

    suspend fun makeACookie(editCookie: EditCookie)

    suspend fun editCookieInfo(editCookie: EditCookie)

    suspend fun getResult(): Boolean
}
