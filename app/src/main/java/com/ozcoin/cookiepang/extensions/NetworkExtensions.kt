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
            NetworkResult.Fail(response.code(), errorMessage = response.errorBody().toString())
        }
    } else {
        NetworkResult.Fail(0, exception)
    }
}

suspend fun <T : Any, E : Any> getDataResult(response: NetworkResult<T>, success: suspend (T) -> E): DataResult<E> {
    val result = when (response) {
        is NetworkResult.Success -> {
            DataResult.OnSuccess(success(response.response))
        }
        is NetworkResult.Fail -> {
            DataResult.OnFail(response.statusCode, response.throwable, response.errorMessage)
        }
    }
    return result
}
