package com.ozcoin.cookiepang.domain.ask

import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

data class Ask(
    val askId: Int? = null,
    val senderUserId: String,
    val receiverUserId: String,
    var question: String,
    val status: AskStatusType? = null,
    var selectedCategory: UserCategory? = null,
    var categoryId: Int = -1
)

fun Ask.toQuestion(): Question {
    return Question(
        questionId = askId ?: -1,
        question = question,
        needToRespond = status == AskStatusType.PENDING,
        categoryId = selectedCategory?.categoryId ?: categoryId
    )
}
