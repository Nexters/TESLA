package com.ozcoin.cookiepang.domain.paging

data class Paging<T>(
    val totalCount: Int,
    val totalPageIndex: Int,
    val nowPageIndex: Int,
    val isLastPage: Boolean,
    val contents: List<T>
)
