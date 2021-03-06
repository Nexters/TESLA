package com.ozcoin.cookiepang.data.request

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val response: T) : NetworkResult<T>()
    data class Fail(
        val statusCode: Int? = null,
        val throwable: Throwable? = null,
        val errorMessage: String? = null
    ) : NetworkResult<Nothing>()
}
