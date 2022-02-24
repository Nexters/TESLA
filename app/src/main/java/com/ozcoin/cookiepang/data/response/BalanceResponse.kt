package com.ozcoin.cookiepang.data.response

import androidx.annotation.Keep
import com.ozcoin.cookiepang.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Keep
@Serializable
data class BalanceResponse(
    @Serializable(with = BigIntegerSerializer::class)
    val balance: BigInteger
)
