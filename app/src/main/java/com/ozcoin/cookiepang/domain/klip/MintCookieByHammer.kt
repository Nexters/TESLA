package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import timber.log.Timber

data class MintCookieByHammer(
    val title: String,
    val content: String,
    val imageUrl: String,
    val tag: String,
    val hammerPrice: Int
)

fun EditCookie.toMintCookieByHammer(): MintCookieByHammer {
    return MintCookieByHammer(
        title = question,
        content = answer,
        imageUrl = CookieCardStyle.YELLOW.imageUrl,
        tag = selectedCategory?.categoryName ?: "",
        hammerPrice = hammerCost.toInt()
    )
}

fun MintCookieByHammer.toKlipParams(): ArrayList<Any> {
    val list = ArrayList<Any>()
    kotlin.runCatching {
        list.add(title)
        list.add(content)
        list.add(imageUrl)
        list.add(tag)
        list.add(hammerPrice.toString())
    }.onFailure { Timber.e(it) }
    return list
}
