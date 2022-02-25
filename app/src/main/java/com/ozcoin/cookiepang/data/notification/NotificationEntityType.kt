package com.ozcoin.cookiepang.data.notification

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class NotificationEntityType {
    Ask, Transaction
}
