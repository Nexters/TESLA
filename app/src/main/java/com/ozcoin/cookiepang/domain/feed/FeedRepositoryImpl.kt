package com.ozcoin.cookiepang.domain.feed

import com.ozcoin.cookiepang.data.category.toData
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.data.timeline.TimeLineRemoteDataSource
import com.ozcoin.cookiepang.data.timeline.toDomain
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.isTypeAll
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
                val response = timeLineRemoteDataSource.getTimeLine(userId, userCategory.toData())
                if (response is NetworkResult.Success) {
                    DataResult.OnSuccess(response.response.contents.map { it.toDomain() })
                } else {
                    DataResult.OnFail
                }
            }
        }

    private suspend fun getAllFeedList(userId: String): DataResult<List<Feed>> =
        withContext(Dispatchers.IO) {
            val response = timeLineRemoteDataSource.getAllTimeLine(userId)
            if (response is NetworkResult.Success) {
                DataResult.OnSuccess(response.response.contents.map { it.toDomain() })
            } else {
                DataResult.OnFail
            }
        }
}
