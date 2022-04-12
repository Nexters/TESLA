package com.ozcoin.cookiepang.data.notification

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.alarm.Alarm
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NotificationEntity(
    val id: Int?,
    val type: NotificationEntityType,
    val title: String,
    val content: String,
    val receiverUserId: Int?,
    val senderUserId: Int?,
    val createdAt: String,
    val askId: Int?,
    val cookieId: Int?
)

fun NotificationEntity.toDomain(): Alarm {
    return Alarm(
        alarmId = id ?: 0,
        title = title,
        type = type.toDomain(),
        contents = content,
        time = createdAt,
        askId = askId,
        cookieId = kotlin.runCatching { cookieId?.toString() ?: "" }.getOrDefault("")
    )
}
