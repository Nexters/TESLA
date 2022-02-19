package com.ozcoin.cookiepang.domain.userinfo

import android.graphics.Bitmap

data class UserInfo(
    var isMine: Boolean = false,
    val userId: String,
    val profileId: String,
    var introduce: String,
    val thumbnailUrl: String? = null,
    var updateThumbnailImg: Bitmap? = null,
    var profileBackgroundImgUrl: String? = null,
    var updateProfileBackgroundImg: Bitmap? = null,
    val collectedCnt: Int,
    val createdCnt: Int,
    val questionCnt: Int
)
