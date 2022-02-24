package com.ozcoin.cookiepang.utils

import java.math.BigInteger

object CoinUnitUtil {

    fun convertToKlaytnUnit(balance: BigInteger): BigInteger {
        return balance.divide(getUnit())
    }

    private fun getUnit(): BigInteger {
        val unit = "1000000000000000000"
        return BigInteger(unit)
    }
}
