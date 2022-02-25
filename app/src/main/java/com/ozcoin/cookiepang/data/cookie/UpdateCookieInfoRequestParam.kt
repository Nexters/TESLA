package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateCookieInfoRequestParam(
    val price: Int? = null,
    val status: String? = null,
    val purchaserUserId: Int? = null
)

fun UpdateCookieInfoRequestParam.toQueryMap(): Map<String, Any> {
    val map = HashMap<String, Any>()
    price?.let { map["price"] = it }
    status?.let { map["status"] = it }
    purchaserUserId?.let { map["purchaserUserId"] = it }
    return map
}
