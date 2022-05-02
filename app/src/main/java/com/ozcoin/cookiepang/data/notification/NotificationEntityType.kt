package com.ozcoin.cookiepang.data.notification

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.alarm.AlarmType
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class NotificationEntityType {
    Ask, Transaction
}

fun NotificationEntityType.toDomain(): AlarmType {
    return when (this) {
        NotificationEntityType.Ask -> AlarmType.ASK
        NotificationEntityType.Transaction -> AlarmType.SALE
    }
}
