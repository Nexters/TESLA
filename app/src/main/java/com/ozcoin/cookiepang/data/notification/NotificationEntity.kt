package com.ozcoin.cookiepang.data.notification

import com.ozcoin.cookiepang.domain.alarm.Alarm
import kotlinx.serialization.Serializable

@Serializable
data class NotificationEntity(
    val askId: Int,
    val content: String,
    val cookieId: Int,
    val createdAt: String,
    val id: Int,
    val receiverUserId: Int,
    val senderUserId: Int,
    val type: NotificationEntityType
)

fun NotificationEntity.toDomain(): Alarm {
    return Alarm(
        alarmId = id,
        title = type.name,
        contents = content,
        time = createdAt
    )
}
