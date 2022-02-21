package com.ozcoin.cookiepang.extensions

import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.utils.DataResult
import retrofit2.Response

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
    var exception: Throwable? = null
    val response = runCatching {
        call.invoke()
    }.onFailure {
        it.printStackTrace()
        exception = it
    }.getOrNull()

    return if (response != null) {
        if (response.isSuccessful) {
            NetworkResult.Success(response = response.body()!!)
        } else {
            NetworkResult.Fail(response.code(), response.errorBody()?.toString() ?: "")
        }
    } else {
        NetworkResult.Fail(0, exception?.message ?: "")
    }
}

suspend fun <T : Any> getDataResult(response: suspend () -> NetworkResult<T>): DataResult<T> {
    val res = response.invoke()
    val result = if (res is NetworkResult.Success) {
        DataResult.OnSuccess(res.response)
    } else {
        DataResult.OnFail
    }
    return result
}
