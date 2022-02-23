package com.ozcoin.cookiepang.utils

object CoinUnitUtil {

    fun convertToKlaytnUnit(balance: Double): Int {
        return (balance / getUnit()).toInt()
    }

    private fun getUnit(): Long {
        var unit = 10L
        repeat(18) {
            unit *= unit
        }
        return unit
    }
}
