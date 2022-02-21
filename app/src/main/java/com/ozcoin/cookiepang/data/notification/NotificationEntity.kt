package com.ozcoin.cookiepang.data.notification

data class NotificationEntity(
    val askId: Int,
    val content: String,
    val cookieId: Int,
    val createdAt: String,
    val id: Int,
    val receiverUserId: Int,
    val senderUserId: Int,
    val type: String
)
