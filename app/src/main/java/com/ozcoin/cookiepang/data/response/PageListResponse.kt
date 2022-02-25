package com.ozcoin.cookiepang.data.response

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PageListResponse<T>(
    val totalCount: Int,
    val totalPageIndex: Int,
    val nowPageIndex: Int,
    val isLastPage: Boolean,
    val contents: List<T>
)
