package com.ozcoin.cookiepang.domain.feed

import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.utils.DataResult

interface FeedRepository {
    suspend fun getFeedList(userId: String, userCategory: UserCategory): DataResult<List<Feed>>

    suspend fun loadMore(userId: String, userCategory: UserCategory): DataResult<List<Feed>>

    fun isLastPage(): Boolean
}
