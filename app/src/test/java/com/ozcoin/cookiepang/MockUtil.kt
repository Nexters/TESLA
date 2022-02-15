package com.ozcoin.cookiepang

object MockUtil {

    fun clearMocks(mocks: List<Any>) {
        mocks.forEach {
            io.mockk.clearMocks(it)
        }
    }
}
