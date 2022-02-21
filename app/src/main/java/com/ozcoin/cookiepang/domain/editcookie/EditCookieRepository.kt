package com.ozcoin.cookiepang.domain.editcookie

interface EditCookieRepository {

    suspend fun makeACookie(editCookie: EditCookie): String

    suspend fun editCookieInfo(editCookie: EditCookie)
}
