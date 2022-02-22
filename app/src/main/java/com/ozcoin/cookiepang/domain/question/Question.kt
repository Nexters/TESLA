package com.ozcoin.cookiepang.domain.question

import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

data class Question(
    val questionId: Int,
    val question: String,
    val category: UserCategory,
    val needToRespond: Boolean
)

fun Question.toEditCookie(): EditCookie {
    val editCookie = EditCookie()
    editCookie.let {
        it.question = question
        it.selectedCategory = category
    }
    return editCookie
}
