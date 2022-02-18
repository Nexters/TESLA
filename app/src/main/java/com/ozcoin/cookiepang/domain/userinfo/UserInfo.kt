package com.ozcoin.cookiepang.domain.userinfo

data class UserInfo(
    var isMine: Boolean = false,
    val userId: String,
    val profileId: String,
    val introduce: String,
    val thumbnailUrl: String? = null,
    val profileBackGroundImgUrl: String? = null,
    val collectedCnt: Int,
    val createdCnt: Int,
    val questions: Int
)
