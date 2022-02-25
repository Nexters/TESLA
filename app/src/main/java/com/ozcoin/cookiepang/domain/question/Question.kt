package com.ozcoin.cookiepang.domain.question

import android.os.Parcelable
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val questionId: Int,
    val question: String,
    val categoryId: Int,
    val needToRespond: Boolean
) : Parcelable

fun Question.toEditCookie(): EditCookie {
    val editCookie = EditCookie()
    editCookie.let {
        it.receivedQuestion = this
        it.question = question
        it.categoryId = categoryId
    }
    return editCookie
}
