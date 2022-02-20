package com.ozcoin.cookiepang.data.cookiedetail

import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory
import kotlinx.serialization.Serializable

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
