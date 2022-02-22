package com.ozcoin.cookiepang.data.cookiedetail

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HistoryEntity(
    val action: HistoryActionType,
    val content: String,
    val createdAt: String
)

fun HistoryEntity.toDomain(): CookieHistory {
    return CookieHistory(
        cookieHistoryType = action.toDomain(),
        contents = content,
        timeStamp = createdAt
    )
}
