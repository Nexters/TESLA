package com.ozcoin.cookiepang.utils

sealed class DataResult<out T : Any> {
    class OnSuccess<out T : Any>(val response: T) : DataResult<T>()
    data class OnFail(
        val errorCode: Int? = null,
        val exception: Throwable? = null,
        val errorMessage: String? = null
    ) : DataResult<Nothing>()
}
