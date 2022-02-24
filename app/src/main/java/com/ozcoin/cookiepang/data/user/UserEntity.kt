package com.ozcoin.cookiepang.data.user

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UserEntity(
    val id: Int? = null,
    val walletAddress: String,
    val nickname: String,
    val introduction: String,
    val profileUrl: String? = null,
    val backgroundUrl: String? = null,
    val status: String? = null,
    val finishOnboard: Boolean? = null
) {
    companion object {
        fun empty() = UserEntity(null, "", "", "")
    }
}

fun UserEntity.toDomain(): User {
    val user = User()
    user.let {
        it.userId = kotlin.runCatching { id.toString() }.getOrDefault("")
        it.walletAddress = walletAddress
        it.profileID = nickname
        it.introduction = introduction
        it.profileUrl = profileUrl
        it.backgroundUrl = backgroundUrl
        it.finishOnboard = finishOnboard ?: false
    }
    return user
}

fun User.toData(): UserEntity = UserEntity(
    id = if (userId.isBlank()) null else userId.toDataUserId(),
    walletAddress = this.walletAddress,
    nickname = profileID,
    introduction = introduction,
    profileUrl = profileUrl,
    backgroundUrl = backgroundUrl,
    status = null
)
