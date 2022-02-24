package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MakeACookieRequestParam(
    val answer: String,
    val authorUserId: Int,
    val categoryId: Int,
    val ownedUserId: Int,
    val price: Int,
    val question: String,
    val txHash: String
)

fun EditCookie.toMakeRequestRemote(userId: Int, txHash: String): MakeACookieRequestParam {
    return MakeACookieRequestParam(
        answer = answer,
        authorUserId = userId,
        ownedUserId = userId,
        categoryId = selectedCategory?.categoryId ?: -1,
        price = hammerCost.toInt(),
        question = question,
        txHash = txHash
    )
}
