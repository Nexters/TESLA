package com.ozcoin.cookiepang.data.timeline

import kotlinx.serialization.Serializable

@Serializable
data class TimeLineResponse(
    val totalCount: Int,
    val totalPageIndex: Int,
    val nowPageIndex: Int,
    val isLastPage: Boolean,
    val contents: List<Content>
)
