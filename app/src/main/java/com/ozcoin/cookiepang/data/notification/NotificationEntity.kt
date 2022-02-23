package com.ozcoin.cookiepang.data.notification

import com.ozcoin.cookiepang.domain.alarm.Alarm
import kotlinx.serialization.Serializable

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
        contents = content,
        time = createdAt
    )
}
