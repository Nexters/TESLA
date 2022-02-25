package com.ozcoin.cookiepang.domain.feed

import com.ozcoin.cookiepang.data.response.PageListResponse
import com.ozcoin.cookiepang.data.timeline.TimeLineEntity
import com.ozcoin.cookiepang.data.timeline.TimeLineRemoteDataSource
import com.ozcoin.cookiepang.data.timeline.toDomain
import com.ozcoin.cookiepang.domain.paging.Paging
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

    private var paging: Paging<Feed>? = null
    private var isLoading = false

    override suspend fun getFeedList(
        userId: String,
        userCategory: UserCategory
    ): DataResult<List<Feed>> {
        return if (userCategory.isTypeAll()) {
            getAllFeedListRemote(userId)
        } else {
            getFeedListRemote(userId, userCategory)
        }
    }

    override suspend fun loadMore(
        userId: String,
        userCategory: UserCategory
    ): DataResult<List<Feed>> =
        withContext(Dispatchers.IO) {
            paging?.let {
                if (!it.isLastPage && !isLoading) {
                    isLoading = true
                    val page = it.nowPageIndex + 1
                    val result = if (userCategory.isTypeAll()) {
                        getAllFeedListRemote(userId, page)
                    } else {
                        getFeedListRemote(userId, userCategory, page)
                    }
                    isLoading = false
                    result
                } else {
                    DataResult.OnFail()
                }
            } ?: DataResult.OnFail()
        }

    override fun isLastPage(): Boolean {
        return paging?.isLastPage ?: true
    }

    private suspend fun getFeedListRemote(
        userId: String,
        userCategory: UserCategory
    ): DataResult<List<Feed>> {
        return getFeedListRemote(userId, userCategory, 0)
    }

    private suspend fun getFeedListRemote(
        userId: String,
        userCategory: UserCategory,
        page: Int
    ): DataResult<List<Feed>> =
        withContext(Dispatchers.IO) {
            getDataResult(timeLineRemoteDataSource.getTimeLine(userId, userCategory, page)) { res ->
                paging = convertToPaging(res)
                kotlin.runCatching { paging?.contents!! }.getOrDefault(emptyList())
            }
        }

    private suspend fun getAllFeedListRemote(userId: String): DataResult<List<Feed>> {
        return getAllFeedListRemote(userId, 0)
    }

    private suspend fun getAllFeedListRemote(userId: String, page: Int) =
        withContext(Dispatchers.IO) {
            getDataResult(timeLineRemoteDataSource.getAllTimeLine(userId, page)) { res ->
                paging = convertToPaging(res)
                kotlin.runCatching { paging?.contents!! }.getOrDefault(emptyList())
            }
        }

    private fun convertToPaging(value: PageListResponse<TimeLineEntity>): Paging<Feed> {
        return Paging(
            totalCount = value.totalCount,
            totalPageIndex = value.totalPageIndex,
            nowPageIndex = value.nowPageIndex,
            isLastPage = value.isLastPage,
            contents = value.contents.map { it.toDomain() }
        )
    }
}
