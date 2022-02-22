package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateCookieInfoRequestParam(
    val price: Int,
    val status: String,
    val purchaserUserId: Int
)

fun UpdateCookieInfoRequestParam.toQueryMap(): Map<String, Any> {
    val map = HashMap<String, Any>()
    map["price"] = price
    map["status"] = status
    map["purchaserUserId"] = purchaserUserId
    return map
}
