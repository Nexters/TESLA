package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistoryType
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.utils.DataResult
import kotlinx.coroutines.delay
import javax.inject.Inject

class CookieDetailRepositoryImpl @Inject constructor() : CookieDetailRepository {

    override suspend fun getCookieDetail(cookieId: String): DataResult<CookieDetail> {
        delay(1000L)

        return DataResult.OnSuccess(
            CookieDetail(
                userCategory = UserCategory("Free Chat", true),
                viewCount = 423,
                question = "나에게 가장 수치스러운 것은?",
                hammerPrice = 24,
                cookieCardStyle = CookieCardStyle.YELLOW,
                collectorThumbnailUrl = "",
                collectorName = "강동구 호랑이3333333",
                creatorThumbnailUrl = "",
                creatorName = "상일동 치타",
                contractAddress = "0xesf232424124214124214",
                tokenAddress = "0xesf232424124214124214",
                cookieHistory = listOf(
                    CookieHistory(
                        CookieHistoryType.PURCHASE,
                        "‘강동구 호랑이' 님이 ‘Q.내가 여자친구가 있을까'를 망치 32개로 수정했습니다.",
                        "2022-02-22 20:00"
                    ),
                    CookieHistory(
                        CookieHistoryType.MODIFY,
                        "‘강동구 호랑이' 님이 ‘Q.내가 여자친구가 있을까'를 망치 34개로 깠습니다.",
                        "2022-10-32 20:00"
                    ),
                    CookieHistory(
                        CookieHistoryType.CREATE,
                        "‘상일동 치타' 님이 ‘Q.내가 여자친구가 있을까'를 망치 34개로 만들었습니다.",
                        "2022-10-32 20:00"
                    )
                )
            )
        )
    }
}
