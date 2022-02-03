package com.ozcoin.cookiepang.request

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val response: T) : NetworkResult<T>()
    data class Fail(val statusCode: Int, val message: String) : NetworkResult<Nothing>()
}
