package com.ozcoin.cookiepang.data.ask

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.ask.AskStatusType
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class AskEntityStateType {
    PENDING, IGNORED, ACCEPTED, DELETED
}

fun AskEntityStateType.toDomain(): AskStatusType {
    return when (this) {
        AskEntityStateType.PENDING -> AskStatusType.PENDING
        AskEntityStateType.IGNORED -> AskStatusType.IGNORED
        AskEntityStateType.ACCEPTED -> AskStatusType.ACCEPTED
        AskEntityStateType.DELETED -> AskStatusType.DELETED
    }
}

fun AskStatusType.toData(): AskEntityStateType {
    return when (this) {
        AskStatusType.PENDING -> AskEntityStateType.PENDING
        AskStatusType.IGNORED -> AskEntityStateType.IGNORED
        AskStatusType.ACCEPTED -> AskEntityStateType.ACCEPTED
        AskStatusType.DELETED -> AskEntityStateType.DELETED
    }
}
