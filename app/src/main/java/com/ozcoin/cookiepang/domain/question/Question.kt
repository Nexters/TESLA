package com.ozcoin.cookiepang.domain.question

import android.os.Parcelable
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val questionId: Int,
    val question: String,
    val category: UserCategory,
    val needToRespond: Boolean
) : Parcelable

fun Question.toEditCookie(): EditCookie {
    val editCookie = EditCookie()
    editCookie.let {
        it.receivedQuestion = this
        it.question = question
        it.selectedCategory = category
    }
    return editCookie
}
