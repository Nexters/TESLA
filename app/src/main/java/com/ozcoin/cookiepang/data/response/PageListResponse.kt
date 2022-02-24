package com.ozcoin.cookiepang.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PageListResponse<T>(
    val totalCount: Int,
    val totalPageIndex: Int,
    val nowPageIndex: Int,
    val isLastPage: Boolean,
    val contents: List<T>
)
