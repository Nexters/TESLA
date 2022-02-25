package com.ozcoin.cookiepang.data.ask

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.ask.Ask
import com.ozcoin.cookiepang.domain.user.toDataUserId
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AskEntity(
    val id: Int? = null,
    val title: String,
    val receiverId: Int,
    val senderId: Int,
    val categoryId: Int,
    val status: AskEntityStateType? = null
)

fun AskEntity.toDomain(): Ask {
    return Ask(
        askId = id,
        senderUserId = senderId.toString(),
        receiverUserId = receiverId.toString(),
        question = title,
        status = status?.toDomain(),
        selectedCategory = null
    )
}

fun Ask.toData(): AskEntity {
    return AskEntity(
        title = question,
        senderId = senderUserId.toDataUserId(),
        receiverId = receiverUserId.toDataUserId(),
        status = status?.toData(),
        categoryId = selectedCategory?.categoryId ?: 0
    )
}
