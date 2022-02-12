package com.ozcoin.cookiepang.domain.feed

import com.ozcoin.cookiepang.domain.usercategory.UserCategory

interface FeedRepository {
    suspend fun getFeedList(userCategory: UserCategory): List<Feed>
}
