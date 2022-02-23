package com.ozcoin.cookiepang.domain.editcookie

interface EditCookieRepository {

    suspend fun makeACookie(userId: String, txHash: String, editCookie: EditCookie): String

    suspend fun editCookieInfo(userId: String, editCookie: EditCookie): Boolean
}
