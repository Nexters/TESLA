package com.ozcoin.cookiepang.domain.feed

import com.ozcoin.cookiepang.data.timeline.TimeLineRemoteDataSource
import com.ozcoin.cookiepang.data.timeline.toDomain
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.isTypeAll
import com.ozcoin.cookiepang.extensions.getDataResult
import com.ozcoin.cookiepang.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val timeLineRemoteDataSource: TimeLineRemoteDataSource
) : FeedRepository {
    override suspend fun getFeedList(
        userId: String,
        userCategory: UserCategory
    ): DataResult<List<Feed>> =
        withContext(Dispatchers.IO) {
            if (userCategory.isTypeAll()) {
                getAllFeedList(userId)
            } else {
                getDataResult(timeLineRemoteDataSource.getTimeLine(userId, userCategory)) { res ->
                    res.contents.map { it.toDomain() }
                }
            }
        }

    private suspend fun getAllFeedList(userId: String): DataResult<List<Feed>> =
        withContext(Dispatchers.IO) {
            getDataResult(timeLineRemoteDataSource.getAllTimeLine(userId)) { res ->
                res.contents.map { it.toDomain() }
            }
        }
}
