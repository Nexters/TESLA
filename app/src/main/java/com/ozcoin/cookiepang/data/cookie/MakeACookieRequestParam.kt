package com.ozcoin.cookiepang.data.cookie

import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.user.toDataUserId
import kotlinx.serialization.Serializable

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

fun EditCookie.toMakeRequestRemote(): MakeACookieRequestParam {
    return MakeACookieRequestParam(
        answer = answer,
        authorUserId = userId.toDataUserId(),
        ownedUserId = userId.toDataUserId(),
        categoryId = selectedCategory?.categoryId ?: -1,
        price = hammerCost.toInt(),
        question = question,
        txHash = tx_hash
    )
}
