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
    val status: AskEntityStateType,
    val receiverId: Int,
    val senderId: Int,
    val categoryId: Int
)

fun AskEntity.toDomain(): Ask {
    return Ask(
        askId = id ?: -1,
        senderUserId = senderId.toString(),
        receiverUserId = receiverId.toString(),
        question = title,
        status = status.toDomain(),
        selectedCategory = null
    )
}

fun Ask.toData(): AskEntity {
    return AskEntity(
        title = question,
        senderId = senderUserId.toDataUserId(),
        receiverId = receiverUserId.toDataUserId(),
        status = status?.toData() ?: AskEntityStateType.PENDING,
        categoryId = selectedCategory?.categoryId ?: 0
    )
}
