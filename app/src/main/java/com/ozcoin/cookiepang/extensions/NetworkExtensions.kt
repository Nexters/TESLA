package com.ozcoin.cookiepang.extensions

import com.ozcoin.cookiepang.request.NetworkResult
import retrofit2.Response


suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
    var exception : Throwable? = null
    val response = runCatching {
        call.invoke()
    }.onFailure {
        it.printStackTrace()
        exception = it
    }.getOrNull()


    return if (response != null) {
        NetworkResult.Success(response = response.body()!!)
    } else {
        NetworkResult.Fail(0, exception?.message!!)
    }
}