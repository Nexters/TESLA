package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class CookieDetailRepositoryImpl @Inject constructor() : CookieDetailRepository {

    private var count = 0

    override suspend fun getCookieDetail(cookieId: String): DataResult<CookieDetail> {
        delay(1000L)

        val result = if (count == 0) {
            DummyUtil.getCookieDetail(false, isHidden = true)
        } else {
            DummyUtil.getCookieDetail(true, isHidden = false)
        }

        count++

        return result
    }
}
