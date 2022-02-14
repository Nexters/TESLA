package com.ozcoin.cookiepang.utils

sealed class DataResult<out T : Any> {
    class OnSuccess<out T : Any>(val response: T) : DataResult<T>()
    object OnFail : DataResult<Nothing>()
}
