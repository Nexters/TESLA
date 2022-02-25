package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateCookieInfoRequestParam(
    val txHash: String? = null,
    val price: Int? = null,
    val status: String? = null,
    val purchaserUserId: Int? = null
)

fun UpdateCookieInfoRequestParam.toQueryMap(): Map<String, Any> {
    val map = HashMap<String, Any>()
    txHash?.let { map["txHash"] = it }
    price?.let { map["price"] = it }
    status?.let { map["status"] = it }
    purchaserUserId?.let { map["purchaserUserId"] = it }
    return map
}
