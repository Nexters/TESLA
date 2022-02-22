package com.ozcoin.cookiepang.data.user

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UserEntity(
    val id: Int,
    val walletAddress: String,
    val nickname: String,
    val introduction: String,
    val profileUrl: String? = null,
    val backgroundUrl: String? = null,
    val status: String? = null,
    val finishOnboard: Boolean? = null
) {
    companion object {
        fun empty() = UserEntity(-1, "", "", "")
    }
}

fun UserEntity.toDomain(): User {
    val user = User()
    user.let {
        it.userId = id.toString()
        it.walletAddress = walletAddress
        it.profileID = nickname
        it.introduction = introduction
        it.profileUrl = profileUrl
        it.backgroundUrl = backgroundUrl
    }
    return user
}

fun User.toData(): UserEntity = UserEntity(
    id = userId.toDataUserId(),
    walletAddress = this.walletAddress,
    nickname = profileID,
    introduction = introduction,
    profileUrl = profileUrl,
    backgroundUrl = backgroundUrl,
    status = null
)
