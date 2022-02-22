package com.ozcoin.cookiepang.data.ask

import com.ozcoin.cookiepang.domain.ask.Ask
import com.ozcoin.cookiepang.domain.user.toDataUserId
import kotlinx.serialization.Serializable

@Serializable
data class AskEntity(
    val id: Int? = null,
    val title: String,
    val status: String? = null,
    val receiverUserId: Int,
    val senderUserId: Int
)

fun AskEntity.toDomain(): Ask {
    return Ask(
        senderUserId = senderUserId.toString(),
        receiverUserId = receiverUserId.toString(),
        question = title,
        selectedCategory = null
    )
}

fun Ask.toData(): AskEntity {
    return AskEntity(
        title = question,
        senderUserId = senderUserId.toDataUserId(),
        receiverUserId = receiverUserId.toDataUserId()
    )
}
